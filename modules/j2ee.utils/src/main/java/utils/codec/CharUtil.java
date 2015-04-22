package utils.codec;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import utils.ExceptionUtil;


/**
 * @author yuanwei
 * @version ctreateTime:2011-8-23 下午4:30:06
 */
public class CharUtil {
	public static String toHexString(char[] chars){
		if(chars==null||chars.length<1)return null;
		StringBuilder result=new StringBuilder(chars.length*7);
		for(char c:chars){
			result.append('0').append('x')
				.append(ByteUtil.toHexChar((c >> 12) & 0xF))
				.append(ByteUtil.toHexChar((c >> 8)  & 0xF))
				.append(ByteUtil.toHexChar((c >> 4)  & 0xF))
				.append(ByteUtil.toHexChar(c & 0xF))
				.append(',');
		}
		if(result.length()>0)result.setLength(result.length()-1);
		return result.toString();
	}
	@Deprecated
	public static String toAsciiString(char[] chars,boolean escapeAll){
		return null;
	}
	public static String native2Ascii(String target,boolean escapeAll){
		if(target==null||target.trim().length()<1)return target;
		StringBuilder result=new StringBuilder(target.length()*2);
		for(char c:target.toCharArray()){
			//http://www.idevelopment.info/data/Programming/character_encodings/PROGRAMMING_character_encodings.shtml
			//\u0000  -  \u007F Basic Latin
			//if(c > '\u007f')result.append("\\u").append(Integer.toString(c,radix));

			if(escapeAll){
				result.append('\\').append('u')
				.append(ByteUtil.toHexChar((c >> 12) & 0xF))
				.append(ByteUtil.toHexChar((c >> 8)  & 0xF))
				.append(ByteUtil.toHexChar((c >> 4)  & 0xF))
				.append(ByteUtil.toHexChar(c & 0xF));
			}else{
				if(c<='\u007f'){
					result.append(c);
				}else if(c>'\u007f'){
					result.append('\\').append('u')
						.append(ByteUtil.toHexChar((c >> 12) & 0xF))
						.append(ByteUtil.toHexChar((c >> 8)  & 0xF))
						.append(ByteUtil.toHexChar((c >> 4)  & 0xF))
						.append(ByteUtil.toHexChar(c & 0xF));
				}
			}
		}
		return result.toString();
	}
	public static String native2Ascii(String target){
		return native2Ascii(target,false);
	}
	/** 转换成\\uxxx */
	public static String native2Ascii4JavaCode(String target,boolean escapeAll){
		if(target==null||target.trim().length()<1)return target;
		StringBuilder result=new StringBuilder(target.length()*2);
		for(char c:target.toCharArray()){
			//http://www.idevelopment.info/data/Programming/character_encodings/PROGRAMMING_character_encodings.shtml
			//\u0000  -  \u007F Basic Latin
			//if(c > '\u007f')result.append("\\u").append(Integer.toString(c,radix));

			if(escapeAll){
				result.append('\\').append('\\').append('u')
				.append(ByteUtil.toHexChar((c >> 12) & 0xF))
				.append(ByteUtil.toHexChar((c >> 8)  & 0xF))
				.append(ByteUtil.toHexChar((c >> 4)  & 0xF))
				.append(ByteUtil.toHexChar(c & 0xF));
			}else{
				if(c<='\u007f'){
					result.append(c);
				}else if(c>'\u007f'){
					result.append('\\').append('\\').append('u')
						.append(ByteUtil.toHexChar((c >> 12) & 0xF))
						.append(ByteUtil.toHexChar((c >> 8)  & 0xF))
						.append(ByteUtil.toHexChar((c >> 4)  & 0xF))
						.append(ByteUtil.toHexChar(c & 0xF));
				}
			}
		}
		return result.toString();
	}
	public static String native2Ascii4JavaCode(String target){
		return native2Ascii4JavaCode(target,false);
	}
	/** 编码值转换成字符 */
	public static String unicodeToString(String str) {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i < str.length();i+=6){// 每次加6
			String str2=str.substring(2 + i,6 + i);// 去掉 \U
			char temp=(char)Integer.parseInt(str2,16);// 把16进制转成10进制.再强转为字符
			sb.append(temp);
		}
		return sb.toString();
	}

	/** mapbar 字符转Unicode编码值 */
	public static String toUnicodeMapbar2006(String target, boolean escapeSpace) {
		if(target==null||target.trim().length()<1)return target;
		int len=target.length();
		StringBuffer outBuffer=new StringBuffer(len * 2);
		for(int x=0;x < len;x++){
			char aChar=target.charAt(x);
			switch(aChar){
			case ' ':
				if(x == 0 || escapeSpace) outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\\':
				outBuffer.append('\\').append('\\');
				break;
			case '\t':
				outBuffer.append('\\').append('t');
				break;
			case '\n':
				outBuffer.append('\\').append('n');
				break;
			case '\r':
				outBuffer.append('\\').append('r');
				break;
			case '\f':
				outBuffer.append('\\').append('f');
				break;
			default:
				if((aChar < 0x0020) || (aChar > 0x007e)){
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(ByteUtil.toHexChar((aChar >> 12) & 0xF));
					outBuffer.append(ByteUtil.toHexChar((aChar >> 8) & 0xF));
					outBuffer.append(ByteUtil.toHexChar((aChar >> 4) & 0xF));
					outBuffer.append(ByteUtil.toHexChar(aChar & 0xF));
				}else{
					if("=: \t\r\n\f#!".indexOf(aChar) != -1) outBuffer.append('\\');
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}
	
	/** mapbar 字符转Unicode编码值 */
	public static String toUnicodeMapbar(String target) {
		if(target==null||target.trim().length()<1)return target;
		int len=target.length();
		StringBuffer outBuffer=new StringBuffer(len * 2);
		for(char aChar:target.toCharArray()){
			switch(aChar){
			case ' ':
				outBuffer.append(' ');
				break;
			case '\\':
				outBuffer.append('\\').append('\\');
				break;
			case '\t':
				outBuffer.append('\\').append('t');
				break;
			case '\n':
				outBuffer.append('\\').append('n');
				break;
			case '\r':
				outBuffer.append('\\').append('r');
				break;
			case '\f':
				outBuffer.append('\\').append('f');
				break;
			default:
				if((aChar < 0x0020) || (aChar > 0x007e)){
					outBuffer.append('\\')
					.append('u')
					.append(ByteUtil.toHexChar((aChar >> 12) & 0xF))
					.append(ByteUtil.toHexChar((aChar >> 8)  & 0xF))
					.append(ByteUtil.toHexChar((aChar >> 4)  & 0xF))
					.append(ByteUtil.toHexChar(aChar & 0xF));
				}else{
					if("=: \t\r\n\f#!".indexOf(aChar) != -1) outBuffer.append('\\');
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	public static String urlEncode(String target,String charset){
		if(target==null||target.trim().length()<1)return target;
		if(charset==null||charset.trim().length()<1)
			charset="UTF-8";
		byte[] bs=null;
		try{
			bs=target.getBytes(charset);
		}catch(UnsupportedEncodingException e){
			ExceptionUtil.newRuntimeException(e);
		}
		if(bs==null)return null;
		StringBuilder buffer=new StringBuilder(bs.length*2);
		for(byte b:bs){
			buffer.append('%').append(ByteUtil.toHexChar(b>>4)).append(ByteUtil.toHexChar(b));
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		System.out.println(native2Ascii("未"));
		System.out.println(native2Ascii("未知用户,请先登录!"));
		System.out.println(native2Ascii("文件太大,请保持在1M以下!"));
		System.out.println(native2Ascii("文件为空!"));
		System.out.println(native2Ascii("文件类型错误,只能上传jpg、jpeg、gif、png格式文件!"));
		System.out.println("\\u6587\\u4ef6\\u592a\\u5927,\\u8bf7\\u4fdd\\u6301\\u57281M\\u4ee5\\u4e0b!");
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("error","1");
		map.put("message","未知用户,请先登录!");//未知用户,请先登录!
		JSONObject json=new JSONObject();
		json.putAll(map);
		System.out.println(json.toString());
		System.out.println(native2Ascii(json.toString(1)));
	}
}
