package utils;

import utils.validate.Checks;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;


public class StringUtil {
	public final static String	EMPTY	="";
    /**
     * return (o1==null ? o2==null : o1.equals(o2));<br/>
     * Returns true if the specified arguments are equal, or both null.
     */
	public static boolean eq(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }
	/**
	 * @param value String 设值
	 * @param defaultString String 默认值
	 * @return String 如果value(设值)为空,返回defaultString(默认值),否则返回value(设值)
	 */
	public static String getValue(String value, String defaultString) {
		return getValue(value,defaultString,!isEmpty(value));
	}
	
	/**
	 * @param value String 设值
	 * @param defaultString String 默认值
	 * @param isTrue boolean 是否用默认值替换设值
	 * @return String if(isTrue)return value;else return defaultString;
	 */
	public static String getValue(String value, String defaultString, boolean isTrue) {
		if(isTrue) return value;
		return defaultString;
	}
	
	/**
	 * 去掉空值
	 * @param text String
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
	public static String toEmpty(String text, String defaultString) {
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
		if(obj == null) return true;
		return isEmpty(obj.toString());
	}
	
	/** Turn special characters into escaped characters conforming to JavaScript. */
	public static String javaScriptEscape(String input) {
		if(input == null){ return input; }
		
		StringBuilder filtered=new StringBuilder(input.length());
		char prevChar='\u0000';
		char c;
		for(int i=0;i < input.length();i++){
			c=input.charAt(i);
			if(c == '"'){
				filtered.append("\\\"");
			}else if(c == '\''){
				filtered.append("\\'");
			}else if(c == '\\'){
				filtered.append("\\\\");
			}else if(c == '/'){
				filtered.append("\\/");
			}else if(c == '\t'){
				filtered.append("\\t");
			}else if(c == '\n'){
				if(prevChar != '\r') filtered.append("\\n");
			}else if(c == '\r'){
				filtered.append("\\n");
			}else if(c == '\f'){
				filtered.append("\\f");
			}else{
				filtered.append(c);
			}
			prevChar=c;
			
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
		if(text == null) return defaultNumber;
		try{
			return Long.parseLong(text);
		}catch(Exception e){
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
		if(text == null) return defaultNumber;
		if(text.indexOf(",") != -1) text=text.replaceAll(",","");
		try{
			return Double.parseDouble(text);
		}catch(Exception e){
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
		if(text == null) return defaultNumber;
		try{
			return Integer.parseInt(text);
		}catch(Exception e){
			return defaultNumber;
		}
	}
	
	/**
	 * 通过正则表达是判断来转化为int类型
	 * @param stringSource String
	 * @return int
	 */
	public static int toInt(String stringSource) {
		if(isEmpty(stringSource)) return -1;
		if(stringSource.matches("[0-9]+")) return Integer.parseInt(stringSource);
		return -1;
	}
	
	/**
	 * 获得text实际长度
	 * @param text String
	 * @return int
	 */
	public static int getRealLength(String text) {
		if(isEmpty(text)) return 0;
		String temp=null;
		int t=0;
		for(int i=0;i < text.length();i++){
			temp=text.substring(i,i + 1);
			// 此处如果temp编码不是GBK,是其他编码temp.getBytes()>=2
			if(temp.getBytes().length > 1){
				t=t + 2;
			}else{
				t=t + 1;
			}
		}
		return t;
	}
	
	/**
	 * 比较两字符串是否值一样,有一个为null则返回false,都为空true <br>
	 * compare(null,null),compare("","") true; <br>
	 * compare(null,""),compare("",null) false;
	 * @param str1 String
	 * @param str2 String
	 * @return boolean
	 */
	public static boolean compare(String str1, String str2) {
		if(str1 == null && str2 == null) return true;
		if(str1 == null || str2 == null) return false;
		return str1.equals(str2);
	}
	
	/**
	 * 比较两字符串是否值一样,有一个为null则返回false,都为空true <br>
	 * compare(null,null),compare("","") true; <br>
	 * compare(null,""),compare("",null) false;
	 * @param str1 String
	 * @param str2 String
	 * @return boolean
	 */
	public static boolean equals(String str1, String str2) {
		return compare(str1,str2);
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		if(str1 == null && str2 == null) return true;
		if(str1 == null || str2 == null) return false;
		return str1.equalsIgnoreCase(str2);
	}
	
	/**
	 * 转换固定长度,toFixedLength(3,3)="003";
	 * @param num int
	 * @param length int
	 * @return String
	 */
	public static String toFixedLength(int num, int length) {
		String temp=Integer.toString(num);
		if(temp.length() < length){
			StringBuilder sb=new StringBuilder();
			for(int i=0;i < (length - temp.length());i++){
				sb.append(0);
			}
			temp=sb.append(temp).toString();
		}
		return temp;
	}
	
	/**
	 * 计算文件大小
	 * @param size int
	 * @return String
	 */
	public static String getFileSize(int size) {
		if(size > (1024 * 1024)){
			return ((float)size / (1024 * 1024) + "").substring(0,4) + "MB";
		}else if(size > 1024){
			return ((float)size / 1024 + "").substring(0,4) + "KB";
		}else return size + "B";
	}
	/**
	 * 计算文件大小
	 * @param size int
	 * @return String
	 */
	public static String getFileSize(long size) {
		if(size > (1024L * 1024 * 1024)){
			return ((float)size / (1024L * 1024* 1024) + "").substring(0,4) + "GB";
		}else if(size > (1024L * 1024)){
			return ((float)size / (1024L * 1024) + "").substring(0,4) + "MB";
		}else if(size > 1024L){
			return ((float)size / 1024L + "").substring(0,4) + "KB";
		}else return size + "B";
	}
	/** 取得两个日期的天数之差 */
	public static long getDaysInterval(Date d1, Date d2) {
		return (d2.getTime() - d1.getTime()) / 86400000;
	}
	
	/** 将字符串格式化为固定长度 */
	public static String toFixedLength(String str, String insert, int len) {
		str=toEmpty(str);
		StringBuilder rs=new StringBuilder();
		for(int i=0,j=(len - str.length());i < j;i++){
			rs.append(insert);
		}
		return rs.append(str).toString();
	}
	
	/** 将字符串格式化为固定长度(右边补空格) */
	public static String toRightFixedLength(String str, String insert, int len) {
		str=toEmpty(str);
		StringBuilder rs=new StringBuilder(str);
		for(int i=0,j=(len - str.length());i < j;i++){
			rs.append(insert);
		}
		return rs.toString();
	}
	
	/** 截取字符串,过长用addString补上 */
	public static String toOmit(String text, int len, String addString) {
		String rs="";
		text=toEmpty(text);
		if(text.length() <= len){
			rs=text;
		}else{
			rs=text.substring(0,len) + addString;
		}
		return rs;
	}
	
	/** 截取字符串,过长用".."补上 */
	public static String toOmit(String str, int len) {
		String rs="";
		if(str == null) str="";
		if(str.length() <= len){
			rs=str;
		}else{
			if(len > 2){
				rs=str.substring(0,len - 2) + "..";
			}
		}
		return rs;
	}
	
	public static boolean checkLength(String text, String mode, int len) {
		if(isEmpty(mode)) mode="<=";
		if(mode.equals("=<")) mode="<=";
		if(len == -1) return true;
		return toEmpty(text).length() <= len;
	}
	
	/**
	 * 分割字符串
	 * @param target String 原始字符串
	 * @param sign String 分隔符
	 * @return String[] 分割后的字符串数组
	 */
	public static String[] split(String target, String sign) {
		if(target == null || sign == null) return null;
		if(target.indexOf(sign) == -1) return new String[]{ target };
		List<String> list=new ArrayList<String>();
		int index;
		while((index=target.indexOf(sign)) != -1){
			list.add(target.substring(0,index));
			target=target.substring(index + sign.length());
		}
		return (String[])list.toArray(new String[0]);
	}
	
	/**
	 * 分割字符串
	 * @param target String 原始字符串
	 * @param sign String 分隔符
	 */
	public static List<String> split2List(String target, String sign, boolean ignoreEmpty) {
		if(isEmpty(target) || sign == null) return null;
		// if(target.indexOf(sign)==-1)return new String[]{target};
		List<String> list=new ArrayList<String>();
		if(sign.trim().length() == 0 || target.indexOf(sign) == -1){
			list.add(target);
		}else{
			int index=0, fromIndex=0;
			String temp=null;
			while(true){
				index=target.indexOf(sign,fromIndex);
				if(index == -1){
					temp=target.substring(fromIndex,target.length());
					if(!ignoreEmpty || !isEmpty(temp)) list.add(temp);
					break;
				}else{
					temp=target.substring(fromIndex,index);
					if(!ignoreEmpty || !isEmpty(temp)) list.add(temp);
					fromIndex=index + sign.length();
				}
			}
		}
		return list;
	}
	
	public static Set<String> splitIds(String ids, String splitString) {
		if(ids == null || splitString == null) return null;
		Set<String> idSet=new HashSet<String>();
		for(String id:ids.split(splitString)){
			if(id != null && id.trim().length() > 0) idSet.add(id);
		}
		if(idSet == null || idSet.isEmpty()) return null;
		return idSet;
	}
	
	public static String uniqueIds(String ids, String splitString) {
		if(ids == null || splitString == null) return "";
		Set<String> idSet=new HashSet<String>();
		for(String id:ids.split(splitString)){
			if(id != null && id.trim().length() > 0) idSet.add(id);
		}
		StringBuilder result=new StringBuilder();
		if(idSet != null && !idSet.isEmpty()){
			for(String id:idSet){
				if(id != null && id.trim().length() > 0) result.append(id).append(",");
			}
			if(result.length() > 0) result.setLength(result.length() - 1);
		}
		
		return result.toString();
	}
	
	public static String joinString(List<String> coll, String delimiter) {
		if(coll == null || coll.isEmpty()) return "";
		
		StringBuilder sb=new StringBuilder();
		
		for(String x:coll)
			sb.append(x).append(delimiter);
		
		sb.delete(sb.length() - delimiter.length(),sb.length());
		
		return sb.toString();
	}
	
	public static String joinString(String[] coll, String delimiter) {
		if(coll == null || coll.length == 0) return "";
		StringBuilder sb=new StringBuilder();
		for(String x:coll)
			sb.append(x).append(delimiter);
		sb.delete(sb.length() - delimiter.length(),sb.length());
		return sb.toString();
	}
	
	public static String joinInteger(List<Integer> coll, String delimiter) {
		if(coll == null || coll.isEmpty()) return "";
		
		StringBuilder sb=new StringBuilder();
		
		for(Integer x:coll)
			sb.append(x).append(delimiter);
		sb.delete(sb.length() - delimiter.length(),sb.length());
		return sb.toString();
	}
	
	public static String joinLong(List<Long> coll, String delimiter) {
		if(coll == null || coll.isEmpty()) return "";
		
		StringBuilder sb=new StringBuilder();
		
		for(Long x:coll)
			sb.append(x).append(delimiter);
		sb.delete(sb.length() - delimiter.length(),sb.length());
		return sb.toString();
	}
	
	/**
	 * Splits a string into substrings based on the supplied delimiter character. Each extracted substring will be trimmed of leading and trailing whitespace.
	 * @param str The string to split
	 * @param delimiter The character that delimits the string
	 * @return A string array containing the resultant substrings
	 */
	public static final List<String> split(String str, char delimiter) {
		// return no groups if we have an empty string
		if((str == null) || "".equals(str)){ return null; }
		
		List<String> parts=new ArrayList<String>();
		int currentIndex;
		int previousIndex=0;
		
		while((currentIndex=str.indexOf(delimiter,previousIndex)) > 0){
			String part=str.substring(previousIndex,currentIndex).trim();
			parts.add(part);
			previousIndex=currentIndex + 1;
		}
		
		parts.add(str.substring(previousIndex,str.length()).trim());
		
		return parts;
	}

	public static boolean isIn(String target, String... strings) {
		if(target != null){
			for(String val:strings){
				if(target.equals(val)) return true;
			}
		}
		return false;
	}
	/** target包含index */
	public static boolean isIndex(String target, String index) {
		if(target != null && index!=null){
			return target.indexOf(index)==-1;
		}
		return false;
	}
	/** target包含index */
	public static boolean isIndexIgnoreCase(String target, String index) {
		if(target != null && index!=null){
			return target.toLowerCase().indexOf(index.toLowerCase())!=-1;
		}
		return false;
	}
	/** target equalsIgnoreCase index */
	public static boolean isEqualsIgnoreCase(String target, String index) {
		if(target != null && index!=null){
			return target.toLowerCase().equalsIgnoreCase((index.toLowerCase()));
		}
		return false;
	}
	
	private static String uuidDigits(long val, int digits, int radix) {
		long hi=1L << (digits * 4);
		return Long.toString(hi | (val & (hi - 1)),radix).substring(1);
	}
	
	/**
	 *
	 * @return (digits(mostSigBits >> 32, 8) + "-" + digits(mostSigBits >> 16, 4) + "-" + digits(mostSigBits, 4) + "-" + digits(leastSigBits >> 48, 4) + "-" + digits(leastSigBits, 12))
	 */
	public static String getUUID36() {
		int radix=36;//radix 2-32,64
		UUID uuid=UUID.randomUUID();
		long mostSigBits=uuid.getMostSignificantBits();
		long leastSigBits=uuid.getLeastSignificantBits();
		
		StringBuilder result=new StringBuilder().append(uuidDigits(mostSigBits >> 32,8,radix)).append(uuidDigits(mostSigBits >> 16,4,radix))
				.append(uuidDigits(mostSigBits >> 4,4,radix))
				
				.append(uuidDigits(leastSigBits >> 48,4,radix)).append(uuidDigits(leastSigBits,12,radix));
		return result.toString();
	}
	public static String getUUID16() {
		return UUID.randomUUID().toString();
	}
	/**
	 * 判断输入的字符串是否为纯汉字
	 * @param str 传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str) {
		if(isEmpty(str)) return false;
		Pattern pattern=Pattern.compile("^[\u0391-\uFFE5]+$");// 所有中文字符
		// Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]+$");//汉字
		return pattern.matcher(str).matches();
	}
	
	/**
	 * double型格式化 例如:33665448856.6568975 --> 33,665,448,856.66
	 * @param num double
	 * @param format String 格式 默认",###.00"
	 * @return String
	 */
	public static String getDoubleString(double num, String format) {
		if(StringUtil.isEmpty(format)) format=",###.00";
		return new DecimalFormat(format).format(num);
	}
	
	public static String mapToString(Map<?, ?> map) {
		if(map == null) return null;
		StringBuilder result=new StringBuilder();
		for(Map.Entry<?, ?> entry:map.entrySet()){
			result.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
		}
		return result.toString();
	}
	
	/** 获得数组第index个元素的值,否则返回defaultValue */
	public static String getArrayIndexValue(Object[] obj, int index, String defaultValue) {
		/* if(obj==null || obj.length<index) return defaultValue; if(obj[index]==null || obj[index].toString().length()<1) return defaultValue; return obj[index].toString(); */
		try{
			return obj[index].toString();
		}catch(Exception e){
			// log.error("RequestUtil.getArrayIndexValue(Object[],index,defaultValue) error:index="+index+"	Exception:"+e.toString());
			return defaultValue;
		}
	}
	
	public static StringBuilder append(StringBuilder builder, Object obj, boolean condition) {
		if(condition){
			return builder.append(obj);
		}else{
			return builder;
		}
	}
	
	public static String toString(Object obj) {
		if(obj == null) return EMPTY;
		/* if(obj.getClass()){ return Arrays.toString } */
		return "";
	}
	
	/**
	 * <p>
	 * Replaces all occurrences of a character in a String with another. This is a null-safe version of {@link String#replace(char, char)}.
	 * </p>
	 * <p>
	 * A <code>null</code> string input returns <code>null</code>. An empty ("") string input returns an empty string.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.replaceChars(null, *, *)        = null
	 * StringUtils.replaceChars("", *, *)          = ""
	 * StringUtils.replaceChars("abcba", 'b', 'y') = "aycya"
	 * StringUtils.replaceChars("abcba", 'z', 'y') = "abcba"
	 * </pre>
	 * @param str String to replace characters in, may be null
	 * @param searchChar the character to search for, may be null
	 * @param replaceChar the character to replace, may be null
	 * @return modified String, <code>null</code> if null string input
	 * @since 2.0
	 */
	public static String replaceChars(String str, char searchChar, char replaceChar) {
		if(str == null){ return null; }
		return str.replace(searchChar,replaceChar);
	}
	public static String enumeration2String(Enumeration<?> e){
		if(e==null)return null;
		StringBuilder builder=new StringBuilder("[");
		while(e.hasMoreElements()){
			builder.append(e.nextElement()).append(",");
		}
		if(builder.length()>1)builder.setLength(builder.length()-1);
		return builder.append("]").toString();
	}

	/**
	 * Replaces all occurrences of a character in a string.
	 *
	 * @param s      input string
	 * @param sub    character to replace
	 * @param with   character to replace with
	 */
	public static String replaceChar(String s, char sub, char with) {
		int startIndex = s.indexOf(sub);
		if (startIndex == -1) {
			return s;
		}
		char[] str = s.toCharArray();
		for (int i = startIndex; i < str.length; i++) {
			if (str[i] == sub) {
				str[i] = with;
			}
		}
		return new String(str);
	}

	/**
	 * Replaces all occurrences of a characters in a string.
	 *
	 * @param s      input string
	 * @param sub    characters to replace
	 * @param with   characters to replace with
	 */
	public static String replaceChars(String s, char[] sub, char[] with) {
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			char c = str[i];
			for (int j = 0; j < sub.length; j++) {
				if (c == sub[j]) {
					str[i] = with[j];
					break;
				}
			}
		}
		return new String(str);
	}
	/**
	 * Finds index of the first character in given array the differs from the
	 * given set of characters.
	 *
	 * @return index of matched character or -1
	 */
	public static int indexOf(char[] source, int index, char[] match) {
		for (int i = index; i < source.length; i++) {
			if ( !Checks.in(source[i], match) ) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds index of the first character in given array the differs from the
	 * given set of characters.
	 *
	 * @return index of matched character or -1
	 */
	public static int indexOf(char[] source, int index, char match) {
		for (int i = index; i < source.length; i++) {
			if (source[i] != match) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds index of the first character in given array the matches any from the
	 * given set of characters.
	 *
	 * @return index of matched character or -1
	 */
	public static int findFirstEqual(char[] source, int index, char[] match) {
		for (int i = index; i < source.length; i++) {
			if (Checks.in(source[i], match)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds index of the first character in given array the matches any from the
	 * given set of characters.
	 *
	 * @return index of matched character or -1
	 */
	public static int findFirstEqual(char[] source, int index, char match) {
		for (int i = index; i < source.length; i++) {
			if (source[i] == match) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Splits a string in several parts (tokens) that are separated by delimiter
	 * characters. Delimiter may contains any number of character and it is
	 * always surrounded by two strings.
	 *
	 * @param src    source to examine
	 * @param d      string with delimiter characters
	 *
	 * @return array of tokens
	 */
	public static String[] splitc(String src, String d) {
		if ((d.length() == 0) || (src.length() == 0) ) {
			return new String[] {src};
		}
		char[] delimiters = d.toCharArray();
		char[] srcc = src.toCharArray();

		int maxparts = srcc.length + 1;
		int[] start = new int[maxparts];
		int[] end = new int[maxparts];

		int count = 0;

		start[0] = 0;
		int s = 0, e;
		if (Checks.in(srcc[0], delimiters)) {	// string starts with delimiter
			end[0] = 0;
			count++;
			s = indexOf(srcc, 1, delimiters);
			if (s == -1) {							// nothing after delimiters
				return new String[] {EMPTY, EMPTY};
			}
			start[1] = s;							// new start
		}
		while (true) {
			// find new end
			e = findFirstEqual(srcc, s, delimiters);
			if (e == -1) {
				end[count] = srcc.length;
				break;
			}
			end[count] = e;

			// find new start
			count++;
			s = indexOf(srcc, e, delimiters);
			if (s == -1) {
				start[count] = end[count] = srcc.length;
				break;
			}
			start[count] = s;
		}
		count++;
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = src.substring(start[i], end[i]);
		}
		return result;
	}

	/**
	 * Splits a string in several parts (tokens) that are separated by single delimiter
	 * characters. Delimiter is always surrounded by two strings.
	 *
	 * @param src           source to examine
	 * @param delimiter     delimiter character
	 *
	 * @return array of tokens
	 */
	public static String[] splitc(String src, char delimiter) {
		if (src.length() == 0) {
			return new String[] {EMPTY};
		}
		char[] srcc = src.toCharArray();

		int maxparts = srcc.length + 1;
		int[] start = new int[maxparts];
		int[] end = new int[maxparts];

		int count = 0;

		start[0] = 0;
		int s = 0, e;
		if (srcc[0] == delimiter) {	// string starts with delimiter
			end[0] = 0;
			count++;
			s = indexOf(srcc, 1, delimiter);
			if (s == -1) {							// nothing after delimiters
				return new String[] {EMPTY, EMPTY};
			}
			start[1] = s;							// new start
		}
		while (true) {
			// find new end
			e = findFirstEqual(srcc, s, delimiter);
			if (e == -1) {
				end[count] = srcc.length;
				break;
			}
			end[count] = e;

			// find new start
			count++;
			s = indexOf(srcc, e, delimiter);
			if (s == -1) {
				start[count] = end[count] = srcc.length;
				break;
			}
			start[count] = s;
		}
		count++;
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = src.substring(start[i], end[i]);
		}
		return result;
	}
	/**
	 * Finds the first occurrence of a character in the given source but within limited range (start, end].
	 */
	public static int indexOf(String src, char c, int startIndex, int endIndex) {
		if (startIndex < 0) {
			startIndex = 0;
		}
		int srclen = src.length();
		if (endIndex > srclen) {
			endIndex = srclen;
		}
		for (int i = startIndex; i < endIndex; i++) {
			if (src.charAt(i) == c) {
				return i;
			}
		}
		return -1;
	}
	public static void main(String[] args) {
		// System.out.println(compare(null,""));
		// System.out.println(isChinese("Α￥传入的字符窜"));
		// for (char i = '\u4e00',j='\u9fa5'; i < j; i++) {
		// System.out.print(i);
		// }
		// 囗囘囙囚四囜囝回囟因囡团団囤囥囦囧囨囩囪囫囬园囮囯困
		// 囱囲図围囵囶囷囸囹固囻囼国图囿圀圁圂圃圄圅圆圇圈圉圊
		// 國圌圍圎圏圐圑園圓圔圕圖圗團圙圚圛圜圝圞
		
		System.out.println(toDouble("33,665,448,856.00",0));
		System.out.println(getDoubleString(33665448856.6568975,",###.00"));
		System.out.println(isIndexIgnoreCase("req.getHeader(Names.UPGRADE)","Upgrade"));
		System.out.println(isIndexIgnoreCase("websocket","WebSocket"));
	}
}
