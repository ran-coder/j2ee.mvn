package utils.db;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Vector;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionPool {
	private static Logger				log			=Logger.getLogger("ConnectionPool");
	private String						driver;
	private String						url;
	private int						size;
	private String						username;
	private String						password;
	private Vector<PooledConnection>	pool;

	public ConnectionPool() {}

	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String driver) {
		this.driver=driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url=url;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size=size;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username=username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	public Vector<PooledConnection> getPool() {
		return pool;
	}

	private Connection createConnection() throws Exception {
		Connection con=null;
		try{

			Properties props=new Properties();
			props.put("user",username);
			props.put("password",password);
			Driver myDriver=(Driver)Class.forName(driver).newInstance();
			con=myDriver.connect(url,props);

		}catch(Exception e){
			log.info("error in createConnection" + e.toString());
			return null;
		}
		return con;
	}

	public synchronized void initializePool() throws Exception {
		if(driver == null){ throw new Exception(""); }
		if(url == null){ throw new Exception(""); }
		if(size < 1){ throw new Exception(""); }
		try{
			for(int i=0;i < size;i++){
				// System.out.println("Opening JDBC Connection " + i);
				Connection con=createConnection();
				if(con != null){
					PooledConnection pcon=new PooledConnection(con);
					addConnection(pcon);
				}
			}
		}catch(Exception e){

		}

	}

	private void addConnection(PooledConnection value) {
		if(pool == null){
			pool=new Vector<PooledConnection>(size);
		}
		pool.addElement(value);
	}

	public synchronized void releaseConnection(Connection con) {
		/**
		 * 连接池监控
		 */

		PooledConnection pcons=null;
		int freeConn=0;
		int usedConn=0;
		for(int i=0;i < pool.size();i++){
			pcons=(PooledConnection)pool.elementAt(i);
			if(pcons.inUse() == false){
				freeConn++;
			}
			if(pcons.inUse() == true){
				usedConn++;
			}
		}

		System.out.println("释放连接前-连接池中连接数 = "+pool.size()) ;
		System.out.println("释放连接前-连接池中空闲连接 = "+freeConn) ;
		System.out.println("释放连接前-连接池中已用连接 = "+usedConn) ;

		for(int i=0;i < pool.size();i++){
			PooledConnection pcon=(PooledConnection)pool.elementAt(i);

			// if(pcon.getConnection() == null){
			if(pcon.getConnection() == con){
				// System.out.println("Releasing Connection "+i) ;
				pcon.setInUse(false);
				break;
			}
		}

		/**
		 * 连接池监控
		 */
		freeConn=0;
		usedConn=0;

		for(int i=0;i < pool.size();i++){
			pcons=(PooledConnection)pool.elementAt(i);
			if(pcons.inUse() == false){
				freeConn++;
			}
			if(pcons.inUse() == true){
				usedConn++;
			}
		}
		// System.out.println("释放连接后-连接池中连接数 = "+pool.size()) ;
		// System.out.println("释放连接后-连接池中空闲连接 = "+freeConn) ;
		// System.out.println("释放连接后-连接池中已用连接 = "+usedConn) ;

	}

	public synchronized Connection getConnection() throws Exception {
		PooledConnection pcon=null;

		/**
		 * 连接池监控
		 */

		int freeConn=0;
		int usedConn=0;
		for(int i=0;i < pool.size();i++){
			pcon=(PooledConnection)pool.elementAt(i);
			if(pcon.inUse() == false){
				freeConn++;
			}
			if(pcon.inUse() == true){
				usedConn++;
			}
		}
		System.out.println("取得连接前-连接池中连接数 = "+pool.size()) ;
		System.out.println("取得连接前-连接池中空闲连接 = "+freeConn) ;
		System.out.println("取得连接前-连接池中已用连接 = "+usedConn) ;

		for(int i=0;i < pool.size();i++){
			pcon=(PooledConnection)pool.elementAt(i);
			if(pcon.inUse() == false){
				pcon.setInUse(true);
				return pcon.getConnection();
			}
		}

		try{
			Connection con=createConnection();
			pcon=new PooledConnection(con);
			pcon.setInUse(true);
			pool.addElement(pcon);
		}catch(Exception e){

		}
		return pcon.getConnection();
	}

	public synchronized void emptyPool() {
		for(int i=0;i < pool.size();i++){
			System.out.println("Closing JDBC Connection " + i);
			PooledConnection pcon=(PooledConnection)pool.elementAt(i);
			if(pcon.inUse() == false){
				pcon.close();
			}else{
				try{
					// java.lang.Thread.sleep( 30000);
					pcon.close();
				}catch(Exception e){
					log.info("连接池关闭连接错误 :" + e.toString());
				}
			}
		}
	}
}

class PooledConnection {
	private Connection	connection	=null;

	private boolean		inuse		=false;

	public PooledConnection(Connection value) {
		if(value != null){
			connection=value;
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setInUse(boolean value) {
		inuse=value;
	}

	public boolean inUse() {
		return inuse;
	}

	public void close() {
		try{
			connection.close();
		}catch(Exception e){

		}
	}
}
