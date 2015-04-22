/**
 * 
 */
package utils.db;

import java.sql.*;

public class SQLite {
	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn=DriverManager.getConnection("jdbc:sqlite:./src/test.db.jsp");
		Statement stmt=conn.createStatement();
		stmt.executeUpdate("drop table if exists people;");
		stmt.executeUpdate("create table people (name, occupation);");
		PreparedStatement ps=conn.prepareStatement("insert into people values (?, ?);");

		ps.setString(1,"Gandhi");
		ps.setString(2,"politics");
		ps.addBatch();
		ps.setString(1,"Turing");
		ps.setString(2,"computers");
		ps.addBatch();
		ps.setString(1,"Wittgenstein");
		ps.setString(2,"smartypants");
		ps.addBatch();

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ResultSet rs=stmt.executeQuery("select * from people;");
		while(rs.next()){
			System.out.println("name = " + rs.getString("name"));
			System.out.println("job = " + rs.getString("occupation"));
		}
		rs.close();
		ps.close();
		stmt.close();
		conn.close();
	}
}