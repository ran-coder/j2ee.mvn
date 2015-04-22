package j2ee.tutorial.lang;

import java.util.EnumMap;
//http://blog.csdn.net/chinakite/archive/2008/11/07/3244934.aspx
public class EnumMapTest {
	private EnumMap<DataBaseType, String>	urls	=new EnumMap<DataBaseType, String>(DataBaseType.class);

	public EnumMapTest() {
		urls.put(DataBaseType.DB2,"jdbc:db2://localhost:5000/sample");
		urls.put(DataBaseType.MYSQL,"jdbc:mysql://localhost/mydb");
		urls.put(DataBaseType.ORACLE,"jdbc:oracle:thin:@localhost:1521:sample");
		urls.put(DataBaseType.SQLSERVER,"jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=mydb");
	}

	/**
	 * 根据不同的数据库类型，返回对应的URL
	 * @param type     DataBaseType枚举类新实例
	 * @return
	 */
	public String getURL(DataBaseType type) {
		return this.urls.get(type);
	}


	public static void main(String[] args) {
		System.out.println(new EnumMapTest().getURL(DataBaseType.MYSQL));
	}

}
