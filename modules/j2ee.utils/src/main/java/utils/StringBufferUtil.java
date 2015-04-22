package utils;

public class StringBufferUtil {
	private StringBuffer rs=new StringBuffer();
	public StringBufferUtil() {	}
	public StringBufferUtil(StringBuffer rs) {
		this.rs=rs;
	}
	public StringBufferUtil(Object obj) {
		if(obj!=null)
		rs.append(obj.toString());
	}
	
	public StringBuffer append(Object obj,boolean add) {
		if(add && obj!=null) {
			return rs.append(obj.toString());
		}else {
			return rs;
		}
	}
	public StringBuffer append(Object obj) {
		return append(obj,true);
	}
	
	public String toString() {
		return rs.toString();
	}
}
