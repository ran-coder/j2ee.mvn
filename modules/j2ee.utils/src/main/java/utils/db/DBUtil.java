/**
 * 
 */
package utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author yuanwei
 *
 */
public class DBUtil {
	private Connection conn = null;
	public DBUtil(){}
	public DBUtil(Connection conn){
		this.conn=conn;
	}
	
	public ResultSet executeQuery(String sql) throws SQLException{
		if(conn == null) throw new SQLException("Connection is null.");
		PreparedStatement ps = null;
		//Statement stmt = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement(sql.toString());
			//stmt=conn.createStatement();
			//ps.setString(1, username);
			rs=ps.executeQuery();
			return rs;
		}catch (SQLException e){
			throw e;
		}finally{
			if(rs != null){
				try{rs.close();}catch (Exception e){}
			}
			if(ps != null){
				try{ps.close();}catch (Exception e){}}
			if(conn != null){ 
				try{conn.close();}catch (Exception e){}
			}
		}
	}
	public void test(){
		//String sql = "select * from userinfo t";
		//String sql="select t.user_pwd  from sys_user t where t.user_id ='admin'";
		//String sql="select *  from sys_user t where rownum<10";
		String sql="select to_char(sysdate,'yyyy-mm-dd hh:MM:ss') time from dual";
		DataSource ds = DataSource.getDataSourceInstance();
		Connection conn = ds.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// System.out.println(rs.getString("id")+"
				// "+rs.getString("username")+" "+rs.getString("password"));
				//System.out.println(rs.getString("user_id")+"	"+rs.getString("user_pwd"));
				System.out.println(rs.getString("time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)rs.close();
			} catch (SQLException e) {
				System.out.println("ResultSet SQLException");
				e.printStackTrace();
			}
			try {
				if(stmt!=null)stmt.close();
			} catch (SQLException e) {
				System.out.println("Statement SQLException");
				e.printStackTrace();
			}
			try {
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println("Connection SQLException");
				e.printStackTrace();
			}
		}
	}
	
	public static void testMSDriver(){
		//Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return;
		}
		System.out.println("数据库连接已开启");
		//String url="jdbc:microsoft:sqlserver//localhost:1433;databasename=test";
		String url="jdbc:sqlserver://localhost;databasename=test";
		String user="admin";
		String password="admin";
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			conn=DriverManager.getConnection(url,user,password);
			st=conn.createStatement();
			rs=st.executeQuery("select getdate()");
			while(rs.next()){
				System.out.println(""+rs.getString(1));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(st!=null){
				try{
					st.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	public static void testMySQLDriver() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("数据库连接已开启..");
		String url="jdbc:mysql://localhost:3306/j2ee";
		String user="admin";
		String password="admin";
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			conn=DriverManager.getConnection(url,user,password);
			st=conn.createStatement();
			rs=st.executeQuery("select NOW()");
			while(rs.next()){
				System.out.println(""+rs.getString(1));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(st!=null){
				try{
					st.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void testConnection(){
		ResultSet rs = null;
		try {
			rs=new DBUtil(DataSource.getDataSourceInstance().getConnection())
			.executeQuery("select to_char(sysdate,'yyyy-mm-dd hh:MM:ss') time from dual");
			while(rs.next()){
				System.out.println(""+rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		//new DBUtil().test();
		testMySQLDriver();
	}
	
}
