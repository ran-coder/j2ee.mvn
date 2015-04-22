package utils;

public class SqlUtil {
	public static String add(String sql,boolean add) {
		return add?sql:"";
	}
	/**
	 * sql = "and a > '$data'" ;data='1'; return "and a > '1' ";
	 * 
	 */
	public static String add(String sql,String data,boolean add) {
		if(StringUtil.isEmpty(sql) || StringUtil.isEmpty(data) || "null".equalsIgnoreCase(data))
			return "";
		data=StringUtil.toEmpty(data);
		return add?sql.replaceAll("\\$data", data):"";
	}
	public static String add(String sql,String data) {
		return add(sql, data, true);
	}
	public static String add(String sql,int data,boolean add) {
		return add(sql, data+"", add);
	}
}
