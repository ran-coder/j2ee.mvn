package tutorial.apache.util.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang.time.StopWatch;

public class DbutilsJDBCTest {
	public static void query() {
		Connection conn=null;
		String jdbcURL="jdbc:mysql://localhost:3306/test";
		String jdbcDriver="com.mysql.jdbc.Driver";
		try{
			DbUtils.loadDriver(jdbcDriver);
			conn=DriverManager.getConnection(jdbcURL,"admin","admin");
			QueryRunner query=new QueryRunner();
			System.out.println("***Using MapListHandler***");
			// 以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
			@SuppressWarnings("rawtypes")
			List list=(List)query.query(conn,"select id,username,password,logintime from user",new MapListHandler());
			// 以下是处理代码，可以抽取出来
			for(int i=0;i < list.size();i++){
				@SuppressWarnings("rawtypes")
				Map vals=(Map)list.get(i);
				System.out.println(vals.get("id") + ":" + vals.get("username"));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
	}

	public static void beanListQuery() {
		Connection conn=null;
		String jdbcURL="jdbc:mysql://localhost:3306/test";
		String jdbcDriver="com.mysql.jdbc.Driver";
		try{
			DbUtils.loadDriver(jdbcDriver);
			// Username "root". Password "root"
			conn=DriverManager.getConnection(jdbcURL,"admin","admin");
			QueryRunner query=new QueryRunner();
			System.out.println("***Using MapListHandler***");
			// 以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
			@SuppressWarnings("rawtypes")
			List list=query.query(conn,"select id,username from user",new BeanListHandler<User>(User.class));
			// 以下是处理代码，可以抽取出来
			for(int i=0;i < list.size();i++){
				User user=(User)list.get(i);
				System.out.println(user);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
	}

	public static void main(String[] args) {
		StopWatch sw=new StopWatch();
		sw.start();
		query();
		sw.stop();
		System.out.println("query() used " + sw.getTime() + " ms.");
		sw.reset();
		sw.start();
		beanListQuery();
		sw.stop();
		System.out.println("beanListQuery() used " + sw.getTime() + " ms.");
	}
}