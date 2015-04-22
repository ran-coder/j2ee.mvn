package j2ee.tutorial.apache.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * @param value String 设值
	 * @param defaultString String 默认值
	 * @return String 如果value(设值)为空,返回defaultString(默认值),否则返回value(设值)
	 */
	public static String getValue(String value,String defaultString) {
		return getValue(value, defaultString, !isEmpty(value));
	}
	/**
	 * @param value String 设值
	 * @param defaultString String 默认值
	 * @param isTrue boolean 是否用默认值替换设值
	 * @return String  if(isTrue)return value;else	return defaultString;
	 */
	public static String getValue(String value,String defaultString,boolean isTrue) {
		if(isTrue)return value;
		return defaultString;
	}

	/**
	 * 去掉空值
	 * @param  text String
	 * @return String
	 */
	public static String toEmpty(String text) {
		return getValue(text,"").trim();
	}
	
	/**
	 * 去掉空值,设置默认为defaultString
	 * @param text String 
	 * @param defaultString String
	 * @return String
	 */
	public static String toEmpty(String text,String defaultString) {
		return getValue(text,defaultString).trim();
	}
	
	/**
	 * 是否为空
	 * @param text String
	 * @return boolean
	 */
	public static boolean isEmpty(String text) {
		return text == null || text.trim().length() == 0;
	}
	/**
	 * 判断对象是否为空
	 * @param obj Object
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj) {
		if(obj==null)return true;
		return isEmpty(obj.toString());
	}
	
	/** Turn special characters into escaped characters conforming to JavaScript. */
	public static String javaScriptEscape(String input) {
		if (input == null) {
			return input;
		}

		StringBuffer filtered = new StringBuffer(input.length());
		char prevChar = '\u0000';
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '"') {
				filtered.append("\\\"");
			}else if (c == '\'') {
				filtered.append("\\'");
			}else if (c == '\\') {
				filtered.append("\\\\");
			}else if (c == '/') {
				filtered.append("\\/");
			}else if (c == '\t') {
				filtered.append("\\t");
			}else if (c == '\n') {
				if (prevChar != '\r')filtered.append("\\n");
			}else if (c == '\r') {
				filtered.append("\\n");
			}else if (c == '\f') {
				filtered.append("\\f");
			}else {
				filtered.append(c);
			}
			prevChar = c;

		}
		return filtered.toString();
	}
	
	/**
	 * 转化为long类型
	 * @param text String
	 * @param defaultNumber long
	 * @return long
	 */
	public static long toLong(String text, long defaultNumber) {
		if(text==null)return defaultNumber;
		try {
			return Long.parseLong(text);
		} catch (Exception e) {
			return defaultNumber;
		}
	}
	/**
	 * 转换字符串为double型,可以含有","
	 * @param text String
	 * @param defaultNumber double
	 * @return double 
	 */
	public static double toDouble(String text, double defaultNumber) {
		if(text==null)return defaultNumber;
		if(text.indexOf(",")!=-1)text=text.replaceAll(",", "");
		try {
			return Double.parseDouble(text);
		} catch (Exception e) {
			return defaultNumber;
		}
	}
	/**
	 * 转化为int类型
	 * @param text String
	 * @param defaultNumber int
	 * @return int
	 */
	public static int toInt(String text, int defaultNumber) {
		if(text==null)return defaultNumber;
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return defaultNumber;
		}
	}
	
	/**
	 * 通过正则表达是判断来转化为int类型
	 * @param stringSource String
	 * @return int
	 */
	public static int toInt(String stringSource) {
		if (isEmpty(stringSource))
			return -1;
		if (stringSource.matches("[0-9]+"))
			return Integer.parseInt(stringSource);
		return -1;
	}
	
	/**
	 * 获得text实际长度
	 * @param text String
	 * @return int
	 */
	public static int getRealLength(String text) {
		if(isEmpty(text))return 0;
		String temp = null;
		int t = 0;
		for (int i = 0; i < text.length(); i++) {
			temp = text.substring(i, i + 1);
			//此处如果temp编码不是GBK,是其他编码temp.getBytes()>=2
			if (temp.getBytes().length > 1) {
				t = t + 2;
			} else {
				t = t + 1;
			}
		}
		return t;
	}
	
	/**
	 * 比较两字符串是否值一样,有一个为null则返回false,都为空true
	 * <br>compare(null,null),compare("","") true;
	 * <br>compare(null,""),compare("",null) false;
	 * @param  str1 String 
	 * @param  str2 String
	 * @return boolean
	 */
	public static boolean compare(String str1,String str2) {
		if(str1==null && str2==null) return true;
		if(str1==null||str2==null) return false;
		return str1.equals(str2);
	}
	
	/**
	 * 比较两字符串是否值一样,有一个为null则返回false,都为空true
	 * <br>compare(null,null),compare("","") true;
	 * <br>compare(null,""),compare("",null) false;
	 * @param  str1 String 
	 * @param  str2 String
	 * @return boolean
	 */
	public static boolean equals(String str1, String str2) {
		return compare(str1, str2);
	}
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if(str1==null && str2==null) return true;
		if (str1 == null || str2 == null)return false;
		return str1.equalsIgnoreCase(str2);
	}

	/**
	 * 转换固定长度,toFixedLength(3,3)="003";
	 * @param num int
	 * @param length int
	 * @return String
	 */
	public static String toFixedLength(int num,int length) {
		String temp = Integer.toString(num);
		if (temp.length() < length) {
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ;i<(length - temp.length());i++){
				sb.append(0);
			}
			temp = sb.append(temp).toString();
		}
		return temp;
	}

	/**
	 * 计算文件大小
	 * @param size int
	 * @return String
	 */
	public static String getFileSize(int size) {
		if (size > (1024 * 1024)) {
			return ((float) size / (1024 * 1024) + "").substring(0, 4) + "MB";
		} else if (size > 1024) {
			return ((float) size / 1024 + "").substring(0, 4) + "KB";
		} else
			return size + "B";
	}

	/** 取得两个日期的天数之差 */
	public static long getDaysInterval(Date d1, Date d2) {
		return (d2.getTime() - d1.getTime()) / 86400000;
	}

	/** 将字符串格式化为固定长度 */
	public static String toFixedLength(String str,String insert, int len) {
		str=toEmpty(str);
		StringBuffer rs=new StringBuffer();
		for (int i = 0,j=(len - str.length()); i < j; i++) {
			rs.append(insert);
		}
		return rs.append(str).toString();
	}

	/** 将字符串格式化为固定长度(右边补空格) */
	public static String toRightFixedLength(String str,String insert, int len) {
		str=toEmpty(str);
		StringBuffer rs=new StringBuffer(str);
		for (int i = 0,j=(len - str.length()); i < j; i++) {
			rs.append(insert);
		}
		return rs.toString();
	}
	
	/** 截取字符串,过长用addString补上 */
	public static String toOmit(String text,int len,String addString) {
		String rs="";
		text=toEmpty(text);
		if(text.length()<=len) {
			rs=text;
		}else {
			rs=text.substring(0,len)+addString;
		}
		return rs;
	}
	/** 截取字符串,过长用".."补上 */
	public static String toOmit(String str,int len){
		String rs="";
		if(str==null)str="";
		if(str.length()<=len){
			rs=str;
		}else{
			if(len>2){
				rs=str.substring(0,len-2)+"..";
			}
		}
		return rs;
	}
	
	public static boolean checkLength(String text,String mode,int len) {
		if(isEmpty(mode))mode="<=";
		if(mode.equals("=<"))mode="<=";
		if(len==-1)return true;
		return toEmpty(text).length()<=len;
	}

	/**
	 * 分割字符串
	 * 
	 * @param str String 原始字符串
	 * @param splitsign String 分隔符
	 * @return String[] 分割后的字符串数组
	 */
	public static String[] split(String str, String splitsign) {
		int index;
		if (str == null || splitsign == null)
			return null;
		ArrayList<String> list = new ArrayList<String>();
		while ((index = str.indexOf(splitsign)) != -1) {
			list.add(str.substring(0, index));
			str = str.substring(index + splitsign.length());
		}
		list.add(str);
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * 判断输入的字符串是否为纯汉字
	 * 
	 * @param str 传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str) {
		if(isEmpty(str))return false;
		Pattern pattern = Pattern.compile("^[\u0391-\uFFE5]+$");//所有中文字符
		//Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]+$");//汉字
		return pattern.matcher(str).matches();
	}
	
	
	/**
	 * double型格式化 例如:33665448856.6568975 --> 33,665,448,856.66
	 * @param num double
	 * @param format String 格式 默认",###.00"
	 * @return String
	 */
	public static String getDoubleString(double num,String format){  
		if(StringUtil.isEmpty(format))format=",###.00";
		return new DecimalFormat(format).format(num);
	}

	public static void main(String[] args) {
		//System.out.println(compare(null,""));
		//System.out.println(isChinese("Α￥传入的字符窜"));
		//for (char i = '\u4e00',j='\u9fa5'; i < j; i++) {
			//System.out.print(i);
		//}
		//囗囘囙囚四囜囝回囟因囡团団囤囥囦囧囨囩囪囫囬园囮囯困
		//囱囲図围囵囶囷囸囹固囻囼国图囿圀圁圂圃圄圅圆圇圈圉圊
		//國圌圍圎圏圐圑園圓圔圕圖圗團圙圚圛圜圝圞

		System.out.println(toDouble("33,665,448,856.00",0));
		System.out.println(getDoubleString(33665448856.6568975,",###.00"));
	}
}
