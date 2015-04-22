package utils.db;

import java.sql.*;
import javax.naming.*;
import org.apache.log4j.Logger;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import utils.config.Constants;

public class DataSource {

	private static Connection conn = null;
	private static DataSource ds = null;
	private static ComboPooledDataSource cds=null;
	private static javax.sql.DataSource javaxds=null;
	
	private static Logger logger=Logger.getLogger(DataSource.class);
	
	public DataSource(){}
	
	public static DataSource getDataSourceInstance(){
		logger.info("DataSource.getDataSourceInstance--ds == null	:"+(ds == null));
		logger.info("DataSource.getDataSourceInstance--conn == null	:"+(conn == null));
		if (ds == null) {
			ds = new DataSource();
		}else{
			conn=ds.getConnection();
		}
		return ds;
	}
	
	public void close(){
		try {
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			logger.info("Connection 关闭失败：");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return getConnection(Constants.DB_SOURCE);
	}
	
	public Connection getConnection(String type){
		boolean isClose=false;
		try {
			isClose=(conn == null || conn.isClosed());
		} catch (Exception e) {	}
		logger.info("DataSource.getConnection--conn.isClosed()	:"+isClose);
		
		if (isClose) {
			if (type.equals("c3p0")){
				this.getConnectionByC3P0();
			} else if(type.equals("jndi")){
				this.getConnectionByJNDI();
			}else{
				this.getConnectionByJDBC();
			}
		}
		
		return conn;
	}

	private void getConnectionByJDBC() {
		String url = Constants.DB_URL;
		String username = Constants.DB_USERNAME;
		String password = Constants.DB_PASSWORD;
		String driver = Constants.DB_DRIVER;

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception ex) {
			try {
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				logger.info("Connection 关闭失败：");
				e.printStackTrace();
			}
			logger.info("JDBC 方式连接数据库失败：");
			ex.printStackTrace();
		}
	}

	private void getConnectionByJNDI() {
		if(javaxds==null) {
			String jndi = Constants.DB_JNDI;
			try {
				Context initCtx = new InitialContext();
				Context ctx = (Context) initCtx.lookup("java:comp/env");
				Object obj = (Object) ctx.lookup(jndi);
				javaxds = (javax.sql.DataSource) obj;
			} catch (Exception ex) {
				logger.info("JNDI 方式连接数据库失败："+jndi);
				ex.printStackTrace();
			}
		}
		try {
			if(javaxds!=null)conn= javaxds.getConnection();
		} catch (Exception ex) {
			try {
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				logger.info("Connection 关闭失败：");
				e.printStackTrace();
			}
		}
	}
	
	private void getConnectionByC3P0() {
		logger.info("DataSource.getConnectionByC3P0--cds==null	:"+(cds==null));
		if(cds==null) {
			String url = Constants.DB_URL;
			String username = Constants.DB_USERNAME;
			String password = Constants.DB_PASSWORD;
			String driver = Constants.DB_DRIVER;
			int maxconn=10;
			int minconn=1;
			int maxstmt=100;

			try {
				cds=new ComboPooledDataSource();
				cds.setDriverClass(driver);
				cds.setJdbcUrl(url);
				cds.setUser(username);
				cds.setPassword(password);
				cds.setMaxPoolSize(maxconn);
				cds.setMinPoolSize(minconn);
				cds.setMaxStatements(maxstmt);
				
			} catch (Exception ex) {
				logger.info("C3P0 初始化失败：");
				ex.printStackTrace();
			}
		}
		try {
			conn=cds.getConnection();
		} catch (Exception ex) {
			try {
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				logger.info("Connection 关闭失败：");
				e.printStackTrace();
			}
			logger.info("C3P0 方式连接数据库失败：");
			ex.printStackTrace();
		}
		
	}
}
