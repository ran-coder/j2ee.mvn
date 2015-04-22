package utils;

public class Json {
	private StringBuffer buff=new StringBuffer("{\n");
	/*public Json() {
		buff=new StringBuffer();
	}
	public Json(String str) {
		buff=new StringBuffer(StringUtil.toEmpty(str));
	}*/
	public Json append(String text,boolean add) {
		if(add)buff.append(text).append(",");
		return this;
	}
	public Json append(String name,String value,boolean add) {
		if(add)buff.append(name).append(":\"").append(value).append("\",");
		return this;
	}
	public String toString() {
		return buff.append("\n}").toString().replaceAll(",\n}", "\n}");
	}
	public static void main(String[] args) {
		Json json=new Json();
		json.append("name","11", true)
			.append("v","222", true)
			.append("v","222", true);
		System.out.println(json);
	}

}
