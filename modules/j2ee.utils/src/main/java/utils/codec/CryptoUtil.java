package utils.codec;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.security.MessageDigest;

public class CryptoUtil {
	public static String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		//char hexDigits[]={'a','1','f','3','b','5','6','7','8','9','0','4','c','d','e','2'};
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static char toHex(int nibble) {
		return new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' }[(nibble & 0xF)];
	}

	public static String saveConvert(String theString, boolean escapeSpace) {
		if (theString == null)
			return "";
		int len = theString.length();
		StringBuffer rs = new StringBuffer(len * 2);
		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					rs.append('\\');
				rs.append(' ');
				break;
			case '\\':
				rs.append('\\').append('\\');
				break;
			case '\t':
				rs.append('\\').append('t');
				break;
			case '\n':
				rs.append('\\').append('n');
				break;
			case '\r':
				rs.append('\\').append('r');
				break;
			case '\f':
				rs.append('\\').append('f');
				break;
			default:
				if ((aChar < 0x0020) || (aChar > 0x007e)) {
					rs.append('\\').append('u').append(toHex((aChar >> 12) & 0xF)).append(toHex((aChar >> 8) & 0xF))
							.append(toHex((aChar >> 4) & 0xF)).append(toHex(aChar & 0xF));
				} else {
					if ("=: \t\r\n\f#!".indexOf(aChar) != -1)
						rs.append('\\');
					rs.append(aChar);
				}
			}
		}
		return rs.toString();
	}

	/**
	 * utf-8 转换成 unicode
	 * @author fanhui
	 * 2007-3-15
	 * @param inStr
	 * @return
	 */
	public static String utf8ToUnicode(String inStr) {
		char[] myBuffer = inStr.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if (ub == UnicodeBlock.BASIC_LATIN) {
				//英文及数字等
				sb.append(myBuffer[i]);
			} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				//全角半角字符
				int j = (int) myBuffer[i] - 65248;
				sb.append((char) j);
			} else {
				//汉字
				short s = (short) myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u" + hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * unicode 转换成 utf-8
	 * @author fanhui
	 * 2007-3-15
	 * @param theString
	 * @return
	 */
	public static String unicodeToUtf8(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	public static int decode(String s) {
		int i = 0;
		char ac[] = s.toCharArray();
		int j = 0;
		for (int k = ac.length; j < k; j++)
			i = 31 * i + ac[j];

		return Math.abs(i);
	}

	public static String change(String s) {
		byte abyte0[] = s.getBytes();
		char ac[] = new char[s.length()];
		int i = 0;
		for (int k = abyte0.length; i < k; i++) {
			int j = abyte0[i];
			if (j >= 48 && j <= 57)
				j = ((j - 48) + 5) % 10 + 48;
			else if (j >= 65 && j <= 90)
				j = ((j - 65) + 13) % 26 + 65;
			else if (j >= 97 && j <= 122)
				j = ((j - 97) + 13) % 26 + 97;
			ac[i] = (char) j;
		}
		return String.valueOf(ac);
	}

	public String base64encode(String str,int mode) {
		String rs=null;
		try {
			if(mode==1) {
				rs=org.apache.commons.codec.binary.Base64.encodeBase64String(str.getBytes("UTF8"));
			}else {
				rs=new String(org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes("UTF8")));
			}
		}catch(UnsupportedEncodingException e) {
			//LogUtil.print("base64加解密出错:"+e.getLocalizedMessage());
		}
		return rs;
	}
	public static void main(String[] args) {
		//long time = System.currentTimeMillis();
		//System.out.println(saveConvert("中文中文中文中文中adad111", false));
		//System.out.println(System.currentTimeMillis() - time);
	}
	
}
