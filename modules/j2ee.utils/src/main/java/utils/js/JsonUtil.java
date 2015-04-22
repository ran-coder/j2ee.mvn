package utils.js;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

public class JsonUtil {
	public static String convert(String name, String data) {
		return new StringBuilder("\"").append(name).append("\":\"").append(data).append("\"").toString();
	}

	public static String convert(String name, int data) {
		return new StringBuilder("\"").append(name).append("\":").append(data).toString();
	}

	public static String convert(String name, long data) {
		return new StringBuilder("\"").append(name).append("\":").append(data).toString();
	}

	public static String convert(String name, double data) {
		return new StringBuilder("\"").append(name).append("\":").append(data).toString();
	}

	public static String convert(String name, Date data) {
		return convert(name,data.toString());
	}

	public static String object2json(Object obj) {
		StringBuilder json=new StringBuilder();
		if(obj == null){
			json.append("\"\""); //对象为null 输出 "" 到 Json
		}else if(obj instanceof String || obj instanceof Integer || obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double || obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte){
			json.append("\"").append(string2json(obj.toString())).append("\""); //对象为字符串、字符、数字等   将内容两端加 "" 输出到 Json
		}else if(obj instanceof Object[]){
			json.append(array2json((Object[])obj));
		}else if(obj instanceof List){
			json.append(list2json((List<?>)obj));
		}else if(obj instanceof Map){
			json.append(map2json((Map<?, ?>)obj));
		}else if(obj instanceof Set){
			json.append(set2json((Set<?>)obj));
		}else{
			json.append(bean2json(obj));
		}
		return json.toString();
	}

	//Bean 字符化  {"ID":"023945","WC":"1","hall":"1","room":"2"}
	public static String bean2json(Object bean) {
		StringBuilder json=new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props=null;
		try{
			props=Introspector.getBeanInfo(bean.getClass(),Object.class).getPropertyDescriptors();
		}catch(IntrospectionException e){
		}
		if(props != null){
			String value=null;
			for(int i=0;i < props.length;i++){
					String name=object2json(props[i].getName());
					try{
						value=object2json(props[i].getReadMethod().invoke(bean));
					}catch(IllegalArgumentException e){
						e.printStackTrace();
					}catch(IllegalAccessException e){
						e.printStackTrace();
					}catch(InvocationTargetException e){
						e.printStackTrace();
					}
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
			}
			json.setCharAt(json.length() - 1,'}');
		}else{
			json.append("}");
		}
		return json.toString();
	}

	//list 字符化  ["123","456"]
	public static String list2json(List<?> list) {
		StringBuilder json=new StringBuilder();
		json.append("[");
		if(list != null && list.size() > 0){
			for(Object obj:list){
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1,']');
		}else{
			json.append("]");
		}
		return json.toString();
	}

	//对象数组 [{"ID":"023945","WC":"1","hall":"1","room":"2"},{"ID":"082384","WC":"1","hall":"2","room":"3"}]
	//适合内容列表,封装为bean发送到JQuery
	public static String array2json(Object[] array) {
		StringBuilder json=new StringBuilder();
		json.append("[");
		if(array != null && array.length > 0){
			for(Object obj:array){
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1,']');
		}else{
			json.append("]");
		}
		return json.toString();
	}

	//map 
	public static String map2json(Map<?, ?> map) {
		StringBuilder json=new StringBuilder();
		json.append("{");
		if(map != null && map.size() > 0){
			for(Entry<?,?> ent:map.entrySet()){
				json.append(object2json(ent.getKey()));
				json.append(":");
				json.append(object2json(ent.getValue()));
				json.append(",");
			}
			json.setCharAt(json.length() - 1,'}');
		}else{
			json.append("}");
		}
		return json.toString();
	}

	//set
	public static String set2json(Set<?> set) {
		StringBuilder json=new StringBuilder();
		json.append("[");
		if(set != null && set.size() > 0){
			for(Object obj:set){
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1,']');
		}else{
			json.append("]");
		}
		return json.toString();
	}

	//字符串
	public static String string2json(String s) {
		if(s == null) return "";
		StringBuilder sb=new StringBuilder();
		for(int i=0;i < s.length();i++){
			char ch=s.charAt(i);
			switch(ch){
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if(ch >= '\u0000' && ch <= '\u001F'){
					String ss=Integer.toHexString(ch);
					sb.append("\\u");
					for(int k=0;k < 4 - ss.length();k++){
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				}else{
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	public static String map2Json(Map<?,?> map){
		if(map==null || map.size()<1)return null;
		StringBuilder result=new StringBuilder("[");
		for(Entry<?,?> en: map.entrySet()){
			result.append("{\"").append(en.getKey()).append("\":\"").append(en.getValue()).append("\"},");
		}
		if(result.length()>1)result.setLength(result.length()-1);
		return result.append("]").toString();
	}
	
	public static void main(String arg[]) {
		String a="abc\ndefg\"";
		System.out.println("String " + JsonUtil.object2json(a));

		List<String> l=new LinkedList<String>();
		l.add("123");
		l.add("456");
		System.out.println("List " + JsonUtil.object2json(l));

		Map<String,String> m=new HashMap<String,String>();
		m.put("a","a-123");
		m.put("b","b-456");
		System.out.println("Map " + JsonUtil.object2json(m));
		System.out.println(map2Json(m));

		Set<String> s=new TreeSet<String>();
		s.add("789");
		s.add("123");
		s.add("456");
		System.out.println("Set " + JsonUtil.object2json(s));

		BigInteger bi=new BigInteger("12345678");
		System.out.println("BigInteger " + JsonUtil.object2json(bi));

	}
}
