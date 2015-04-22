package utils.codec;

import java.io.*;

public class EncodeChanger {
	public static String native2Ascii(String uniStr) {
		StringBuffer ret=new StringBuffer();
		if(uniStr == null) return null;
		int maxLoop=uniStr.length();
		for(int i=0;i < maxLoop;i++){
			char character=uniStr.charAt(i);
			if(character <= '\177'){
				ret.append(character);
			}else{
				ret.append("\\u");
				String hexStr=Integer.toHexString(character);
				int zeroCount=4 - hexStr.length();
				for(int j=0;j < zeroCount;j++)
					ret.append('0');

				ret.append(hexStr);
			}
		}

		return ret.toString();
	}

	public static String decode(String unicodeStr) {
		if(unicodeStr == null) return null;
		StringBuffer retBuf=new StringBuffer();
		int maxLoop=unicodeStr.length();
		for(int i=0;i < maxLoop;i++)
			if(unicodeStr.charAt(i) == '\\'){
				if(i < maxLoop - 5 && (unicodeStr.charAt(i + 1) == 'u' || unicodeStr.charAt(i + 1) == 'U')) try{
					retBuf.append((char)Integer.parseInt(unicodeStr.substring(i + 2,i + 6),16));
					i+=5;
				}catch(NumberFormatException _ex){
					retBuf.append(unicodeStr.charAt(i));
				}
				else retBuf.append(unicodeStr.charAt(i));
			}else{
				retBuf.append(unicodeStr.charAt(i));
			}

		return retBuf.toString();
	}

	public static String native2asciiWithoutComment(String uniStr) throws IOException {
		StringBuffer buf=new StringBuffer();
		BufferedReader reader=new BufferedReader(new StringReader(uniStr));
		boolean continueFlg=false;
		for(String line=null;(line=reader.readLine()) != null;){
			if((line.trim().startsWith("#") || line.trim().startsWith("!")) && !continueFlg){
				buf.append(line);
			}else{
				if(line.endsWith("\\")) continueFlg=true;
				else continueFlg=false;
				buf.append(native2Ascii(line));
			}
			buf.append("\n");
		}

		if(!uniStr.endsWith("\n")) buf.deleteCharAt(buf.length() - 1);
		return buf.toString();
	}
	
	public static void main(String[] args) throws IOException {
		String str="中文 中斷";
		System.out.println(native2Ascii(str));
		System.out.println(native2asciiWithoutComment(str));
		System.out.println(decode("\u4e2d\u65B7"));
	}
}
