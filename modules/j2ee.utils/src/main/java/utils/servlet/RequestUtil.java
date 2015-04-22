package utils.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 处理HttpServletRequest
 */
public class RequestUtil {
	/** 从文件读取文件 */
	public static void getAttachment(HttpServletResponse response,String filename) throws IOException{
		ServletOutputStream o = response.getOutputStream();
		String path="E:/doc/My Pictures/";
		File file = new File(path + filename);
		if(file.exists() && file.length() > 0){
			response.setContentType("");
			response.addHeader("Content-Disposition",
				new String(("attachment;filename=").getBytes("GBK"),"ISO-8859-1"));
			//response.addHeader("Content-Disposition",new String(("filename="+filename).getBytes("GBK"), "ISO-8859-1"));
			InputStream is = new FileInputStream(file);
			byte[] bs = new byte[1024];
			int len = -1;
			while ((len = is.read(bs)) > 0) {
				o.write(bs, 0, len);
			}
			is.close();
		}else{
			response.setContentType("text/html");
			o.println("Not Found");
		}
		o.flush();
		o.close();
	}
	
	public static void getUrl(HttpServletResponse response,String contentType,byte[] data) throws Exception{
		ServletOutputStream sos = response.getOutputStream();
		if(data.length>0){
			//Content-Type: text/html;charset=gb2312
			//Content-Type: image/gif
			//Content-Type: image/png
			//Content-Type: image/jpeg
			//text/css; charset=GBK
			//application/x-javascript; charset=GBK
			response.setContentType(contentType);
			//response.setHeader("Cache-Control", "no-cache");
			//下载后另存为默认的名称
			//response.addHeader("Content-Disposition","attachment;filename=");
			sos.write(data, 0, data.length);
		}else{
			response.setContentType("text/html");
			sos.println("Not Found");
		}
		sos.flush();
		sos.close();
	}


	public void setCookie(ServletResponse servletresponse) {
		HttpServletResponse response = (HttpServletResponse) servletresponse;

		Cookie client = new Cookie("Client_Language", "");
		client.setMaxAge(60 * 60 * 24 * 365 * 5);//
		client.setPath("/");
		client.setComment("");
		response.addCookie(client);
	}

	public static Hashtable<String, String[]> parseQueryString(String s) {
		String valArray[] = null;
		if (s == null) {
			throw new IllegalArgumentException();
		}
		Hashtable<String, String[]> ht = new Hashtable<String, String[]>();
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				// should give more detail about the illegal argument
				throw new IllegalArgumentException();
			}
			String key = parseName(pair.substring(0, pos), sb);
			String val = parseName(pair.substring(pos + 1, pair.length()), sb);
			if (ht.containsKey(key)) {
				String oldVals[] = ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++)
					valArray[i] = oldVals[i];
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}

	static private String parseName(String s, StringBuffer sb) {
		sb.setLength(0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(s.substring(i + 1, i + 3), 16));
					i += 2;
				} catch (NumberFormatException e) {
					// need to be more specific about illegal arg
					throw new IllegalArgumentException();
				} catch (StringIndexOutOfBoundsException e) {
					String rest = s.substring(i);
					sb.append(rest);
					if (rest.length() == 2)
						i++;
				}
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	public static String get(HttpServletRequest request, String param,String defaultValue) {
		return request.getParameter(param);
	}
	public static int get(HttpServletRequest request, String param,int defaultValue) {
		return defaultValue;
	}
	public static long get(HttpServletRequest request, String param,long defaultValue) {
		return defaultValue;
	}
	public static double get(HttpServletRequest request, String param,double defaultValue) {
		return defaultValue;
	}

	/**
	 * Convenience method to set a cookie
	 *
	 * @param response the current response
	 * @param name the name of the cookie
	 * @param value the value of the cookie
	 * @param path the path to set it on
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath(path);
		cookie.setMaxAge(3600 * 24 * 30); // 30 days

		response.addCookie(cookie);
	}

	/**
	 * Convenience method to get a cookie by name
	 *
	 * @param request the current request
	 * @param name the name of the cookie to find
	 *
	 * @return the cookie (if found), null if not found
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;

		if (cookies == null) {
			return returnCookie;
		}

		for (Cookie thisCookie : cookies) {
			if (thisCookie.getName().equals(name)) {
				// cookies with no value do me no good!
				if (!thisCookie.getValue().equals("")) {
					returnCookie = thisCookie;

					break;
				}
			}
		}

		return returnCookie;
	}

	/**
	 * Convenience method for deleting a cookie by name
	 *
	 * @param response the current web response
	 * @param cookie the cookie to delete
	 * @param path the path on which the cookie was set (i.e. /appfuse)
	 */
	public static void deleteCookie(HttpServletResponse response, Cookie cookie, String path) {
		if (cookie != null) {
			// Delete the cookie by setting its maximum age to zero
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}

	/**
	 * Convenience method to get the application's URL based on request
	 * variables.
	 *
	 * @param request the current request
	 * @return URL to application
	 */
	public static String getAppURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String getParameters(HttpServletRequest request){
		java.util.Enumeration<String> reqs=request.getParameterNames();
		String temp=null;
		while(reqs.hasMoreElements()){
			temp=reqs.nextElement();
			if(temp!=null)
				System.out.println(temp+"="+request.getParameter(temp));
		}
		return null;
	}
	
	public static void main(String[] args) {
		Hashtable<?, ?> list=parseQueryString("1=111&2=123&3=2222");
		//Hashtable list=new Hashtable();
		//list.put("a", "111");
		//list.put("b", "222");
		System.out.println(list.containsKey("1"));
		Enumeration<?>   e   =   list.keys();  
		while(e.hasMoreElements()){  
			Object   key   =   e.nextElement();
			String[] values=(String[]) list.get(key);
			for (int i = 0; i < values.length; i++) {
				System.out.println(key+"="+values[i]);  
			}
		}

		//System.out.println(list);
		//System.out.println("11111111111");
	}
}
