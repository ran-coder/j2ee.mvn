package utils.codec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 字符编码工具类
 */
public class CharSetUtil {
	/**
	 * 转换编码 ISO-8859-1到GB2312
	 * @param text
	 * @return
	 */
	public String iso2GB(String text) {
		String result = null;
		try {
			result = new String(text.getBytes("ISO-8859-1"), "GB2312");
		} catch (UnsupportedEncodingException ex) {
			result = ex.toString();
		}
		return result;
	}

	/**
	 * 转换编码 GB2312到ISO-8859-1
	 * 
	 * @param text
	 * @return
	 */
	public String gb2ISO(String text) {
		String result = null;
		try {
			result = new String(text.getBytes("GB2312"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Utf8URL编码
	 * 
	 * @param s
	 * @return
	 */
	public String utf8URLencode(String text) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();
	}

	/**
	 * Utf8URL解码
	 * 
	 * @param text
	 * @return
	 */
	public String utf8URLdecode(String text) {
		StringBuilder result = new StringBuilder();
		int p = 0;
		if (text != null && text.length() > 0) {
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if (p == -1)
				return text;
			while (p != -1) {
				result.append(text.substring(0, p));
				text = text.substring(p, text.length());
				if (text.length() < 9)return result.toString();
				result.append(codeToWord(text.substring(0, 9)));
				text = text.substring(9, text.length());
				p = text.indexOf("%e");
			}
		}
		return result + text;
	}

	/**
	 * utf8URL编码转字符
	 * 
	 * @param text
	 * @return
	 */
	private String codeToWord(String text) {
		String result;
		if (utf8CodeCheck(text)) {
			byte[] code = new byte[3];
			code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
			code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
			code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
			try {
				result = new String(code, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
				result = null;
			}
		} else {
			result = text;
		}
		return result;
	}

	/**
	 * * 编码是否有效 *
	 * 
	 * @param text *
	 * @return
	 */
	private boolean utf8CodeCheck(String text) {
		StringBuilder sign = new StringBuilder();
		if (text.startsWith("%e")){
			for (int p = 0; p != -1;) {
				p = text.indexOf("%", p);
				if (p != -1)	p++;
				sign.append(p);
			}
		}
		return "147-1".equals(sign.toString());
	}

	/**
	 * 是否Utf8Url编码
	 * 
	 * @param text
	 * @return
	 */
	public boolean isUtf8Url(String text) {
		text = text.toLowerCase();
		int p = text.indexOf("%");
		if (p != -1 && text.length() - p > 9) {
			text = text.substring(p, p + 9);
		}
		return utf8CodeCheck(text);
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		CharSetUtil charSetUtil = new CharSetUtil();
		String url;
		url = "http://www.google.com/search?hl=zh-CN&newwindow=1&q=%E4%B8%AD%E5%9B%BD%E5%A4%A7%E7%99%BE%E7%A7%91%E5%9C%A8%E7%BA%BF%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2&btnG=%E6%90%9C%E7%B4%A2&lr=";
		if (charSetUtil.isUtf8Url(url)) {
			System.out.println(charSetUtil.utf8URLdecode(url));
		} else {
			System.out.println(URLDecoder.decode(url));
		}
		url = "http://www.baidu.com/baidu?word=%D6%D0%B9%FA%B4%F3%B0%D9%BF%C6%D4%DA%CF%DF%C8%AB%CE%C4%BC%EC%CB%F7&tn=myie2dg";
		if (charSetUtil.isUtf8Url(url)) {
			System.out.println(charSetUtil.utf8URLdecode(url));
		} else {
			System.out.println(URLDecoder.decode(url));
		}
	}

}
