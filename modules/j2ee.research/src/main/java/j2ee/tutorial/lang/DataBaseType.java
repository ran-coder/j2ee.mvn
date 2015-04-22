package j2ee.tutorial.lang;

import java.util.EnumSet;
/*
 * 1. 枚举类型不能使用extends关键字，但是可以使用implements关键字。这样我们可以把不同枚举类型共有的行为提取到接口中，来规范枚举类型的行为。
 * 2. 枚举类型的自定义构造函数并不能覆盖默认执行的构造函数，它会跟在默认构造函数之后执行。
 * 3. 枚举类型的自定义构造函数必须是私有的。你不需要在构造函数上添加private关键字，编译器会为我们代劳的。
 * 4. 枚举类型中枚举常量的定义必须放在最上面，其后才能是变量和方法的定义。
 */
public enum DataBaseType {
	MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/mydb"),
	ORACLE("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@localhost:1521:sample"),
	DB2("com.ibm.db2.jdbc.app.DB2Driver","jdbc:db2://localhost:5000/sample"),
	SQLSERVER("com.microsoft.jdbc.sqlserver.SQLServerDriver","jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=mydb");

	private String	driver;
	private String	url;

	//private 自定义的构造函数，它为驱动、URL赋值
	private DataBaseType(String driver, String url) {
		this.driver=driver;
		this.url=url;
	}

	/**
	 * 获得数据库驱动
	 * 
	 * @return
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * 获得数据库连接URL
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	public static void main(String[] args) {
		for(DataBaseType dataBaseType:EnumSet.allOf(DataBaseType.class)){
			System.out.println("the driver is : " + dataBaseType.getDriver());
			System.out.println("the url is : " + dataBaseType.getUrl());
		}
	}
}
