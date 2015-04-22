package j2ee.research.spring.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.StringUtil;

/**
 * Convenience class for setting and retrieving cookies.
 */
public class RequestUtil {
	private transient static Log	log	=LogFactory.getLog(RequestUtil.class);
	
	/**
	 * Convenience method to set a cookie
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path) {
		if(log.isDebugEnabled()){
			log.debug("Setting cookie '" + name + "' on path '" + path + "'");
		}
		
		Cookie cookie=new Cookie(name,value);
		cookie.setSecure(false);
		cookie.setPath(path);
		cookie.setMaxAge(3600 * 24 * 30); // 30 days
		
		response.addCookie(cookie);
	}
	
	public static String getRequestValue(HttpServletRequest request, String name, String defaultstring) {
		return request.getParameter(name) == null ? defaultstring : request.getParameter(name);
	}
	
	/**
	 * Convenience method to get a cookie by name
	 * @param request the current request
	 * @param name the name of the cookie to find
	 * @return the cookie (if found), null if not found
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies=request.getCookies();
		Cookie returnCookie=null;
		
		if(cookies == null){ return returnCookie; }
		
		for(int i=0;i < cookies.length;i++){
			Cookie thisCookie=cookies[i];
			
			if(thisCookie.getName().equals(name)){
				// cookies with no value do me no good!
				if(!thisCookie.getValue().equals("")){
					returnCookie=thisCookie;
					
					break;
				}
			}
		}
		
		return returnCookie;
	}
	
	/**
	 * Convenience method for deleting a cookie by name
	 * @param response the current web response
	 * @param cookie the cookie to delete
	 * @param path the path on which the cookie was set (i.e. /appfuse)
	 */
	public static void deleteCookie(HttpServletResponse response, Cookie cookie, String path) {
		if(cookie != null){
			// Delete the cookie by setting its maximum age to zero
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}
	
	/**
	 * Convenience method to get the application's URL based on request variables.
	 */
	public static String getAppURL(HttpServletRequest request) {
		StringBuffer url=new StringBuffer();
		int port=request.getServerPort();
		if(port < 0){
			port=80; // Work around java.net.URL bug
		}
		String scheme=request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))){
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}
	
	public static String getAppURL(HttpServletRequest request, String bookyurl) {
		StringBuffer url=new StringBuffer();
		int port=request.getServerPort();
		if(port < 0){
			port=80; // Work around java.net.URL bug
		}
		String scheme=request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))){
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		String result=url.toString();
		if(result.indexOf(bookyurl) >= 0){
			result="http://www.hellobooky.com";
		}
		return result;
		
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip=request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip=request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip=request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip=request.getRemoteAddr();
		}
		// catch two ip addresses like this(60.209.102.111, 218.247.244.6),first one is we wanted.
		if(ip != null){
			String[] ipArray=ip.split(",");
			if(ipArray.length == 2){
				ip=ipArray[0];
			}
		}
		return ip;
	}
	
	/** http://xxx/a.html return a.html */
	public static String getURI(HttpServletRequest request) {
		return request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1,request.getRequestURI().length());
	}
	
	/** return http://xxx/a.html?aaa */
	public static StringBuilder getRequestURL(HttpServletRequest request) {
		StringBuilder url=new StringBuilder(1024);// .append(request.getRequestURL()).append('?');
		@SuppressWarnings("unchecked")
		java.util.Enumeration<String> reqs=request.getParameterNames();
		String name=null;
		String[] values=null;
		while(reqs.hasMoreElements()){
			name=reqs.nextElement();
			// System.out.println(name + "	=	" + java.util.Arrays.toString(request.getParameterValues(name)));
			values=request.getParameterValues(name);
			if(values != null){
				for(String val:values){
					url.append(name).append('=').append(val == null ? "" : val).append('&');
				}
			}else{
				url.append(name).append('&');
			}
		}
		if(url.length() > 0){
			url.insert(0,'?').insert(0,request.getRequestURL());
		}
		return url;
	}
	
	/** return http://xxx/a.html?aaa */
	public static StringBuilder getShortRequestURL(HttpServletRequest request) {
		String querys=request.getQueryString();
		StringBuilder url=new StringBuilder(200);// .append(request.getRequestURL()).append('?');
		url.append(request.getRequestURL());
		if(querys != null){
			url.append('?').append(querys);
		}
		
		return url;
	}
	
	/** request.getSession().getServletContext().getAttribute 全局变量 */
	public static Object getContextAttribute(HttpServletRequest request, String key, Object defaultValue) {
		Object obj=null;
		if(key != null){
			obj=request.getSession().getServletContext().getAttribute(key);
		}
		if(obj == null && defaultValue != null) return defaultValue;
		return obj;
	}
	
	/** request.getSession().getAttribute */
	public static Object getSessionAttribute(HttpServletRequest request, String key, Object defaultValue) {
		Object obj=null;
		if(key != null){
			obj=request.getSession().getAttribute(key);
		}
		if(obj == null && defaultValue != null) return defaultValue;
		return obj;
	}
	
	public static void removeSessionAttribute(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}
	
	public static void responseData(HttpServletResponse response, String data, String contentType) {
		contentType=toEmpty(contentType,"application/json");
		if("js".equalsIgnoreCase(contentType)) contentType="text/javascript;charset=UTF-8";
		if("json".equalsIgnoreCase(contentType)) contentType="application/json;charset=UTF-8";
		if("xml".equalsIgnoreCase(contentType)) contentType="text/xml;charset=UTF-8";
		if("html".equalsIgnoreCase(contentType)) contentType="text/html;charset=UTF-8";
		OutputStream os=null;
		response.setHeader("Content-Type",contentType);
		try{
			os=response.getOutputStream();
			os.write(data.getBytes("UTF-8"));
		}catch(Exception e){
			log.error("RequestUtil.responseData-catchException",e);
		}finally{
			if(os != null){
				try{
					os.flush();
				}catch(IOException e){
					e.printStackTrace();
					log.error("RequestUtil.responseData-catchException",e);
				}
				try{
					os.close();
				}catch(IOException e){
					log.error("RequestUtil.responseData-catchException",e);
				}
			}
		}
		
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
	
	public static String toEmpty(Object o) {
		return toEmpty(o,"");
	}
	
	public static String toEmpty(Object o, String defaultString) {
		if(o == null || o.toString() == null) return defaultString;
		else return o.toString();
	}
	
	public static Integer parseInt(String str, Integer defaultValue) {
		try{
			return new Integer(str.split("\\.")[0]);
		}catch(Exception e){
		}
		return defaultValue;
	}
	
	public static Double parseDouble(String str, Double defaultValue) {
		try{
			return new Double(str);
		}catch(Exception e){
		}
		return defaultValue;
	}
	
	public static Long parseLong(String str, Long defaultValue) {
		try{
			return new Long(str.split("\\.")[0]);
		}catch(Exception e){
			// log.error("RequestUtil.parseLong(str,defaultValue) error:str="+str+"	Exception:"+e.toString());
		}
		return defaultValue;
	}
	
	/** 获得数组第index个元素的值,否则返回defaultValue */
	public static String getArrayIndexValue(Object[] obj, int index, String defaultValue) {
		/*
		 * if(obj==null || obj.length<index) return defaultValue; if(obj[index]==null || obj[index].toString().length()<1) return defaultValue; return obj[index].toString(); */
		try{
			return obj[index].toString();
		}catch(Exception e){
			// log.error("RequestUtil.getArrayIndexValue(Object[],index,defaultValue) error:index="+index+"	Exception:"+e.toString());
			return defaultValue;
		}
	}
	
	public static String get(HttpServletRequest request, String param, String defaultValue) {
		return getValue(request.getParameter(param),defaultValue);
	}
	
	public static int get(HttpServletRequest request, String param, int defaultValue) {
		try{
			return Integer.parseInt(get(request,param,"" + defaultValue));
		}catch(Exception e){
		}
		return defaultValue;
	}
	
	public static long get(HttpServletRequest request, String param, long defaultValue) {
		try{
			return Long.parseLong(get(request,param,"" + defaultValue));
		}catch(Exception e){
		}
		return defaultValue;
	}
	
	public static double get(HttpServletRequest request, String param, double defaultValue) {
		try{
			return Double.parseDouble(get(request,param,"" + defaultValue));
		}catch(Exception e){
		}
		return defaultValue;
	}
	
	public static Date get(HttpServletRequest request, String param, String format, Date defaultValue) {
		try{
			return new SimpleDateFormat(format,Locale.ENGLISH).parse(request.getParameter(param));
			// return DateUtil.string2Date( request.getParameter(param) ,format);
		}catch(Exception e){
			// log.error("RequestUtil.get(request,param,format,Date) error:param="+param+"	Exception:"+e.toString());
		}
		return defaultValue;
	}
	
	/** format MM/dd/yyyy */
	public static Date getDate(HttpServletRequest request, String param, Date defaultValue) {
		return get(request,param,"MM/dd/yyyy",defaultValue);
	}
	
	public static List<String> get(HttpServletRequest request, String param, boolean ingoreEmpty) {
		String[] values=request.getParameterValues(param);
		if(values != null && values.length > 0){
			List<String> result=new ArrayList<String>(values.length);
			if(ingoreEmpty){
				for(String value:values){
					if(!isEmpty(value)){
						result.add(value);
					}
				}
			}else{
				for(String value:values){
					result.add(value);
				}
			}
			if(result.isEmpty()) return null;
			return result;
		}
		return null;
	}
	
	public static List<Integer> getIntList(HttpServletRequest request, String param, Integer defaultValue, boolean ingoreEmpty) {
		String[] values=request.getParameterValues(param);
		if(values != null && values.length > 0){
			List<Integer> result=new ArrayList<Integer>(values.length);
			if(ingoreEmpty){
				for(String value:values){
					if(!isEmpty(value)){
						result.add(parseInt(value,defaultValue));
					}
				}
			}else{
				for(String value:values){
					result.add(parseInt(value,defaultValue));
				}
			}
			if(result.isEmpty()) return null;
			return result;
		}
		return null;
	}
	
	public static List<Long> getLongList(HttpServletRequest request, String param, Long defaultValue, boolean ingoreEmpty) {
		String[] values=request.getParameterValues(param);
		if(values != null && values.length > 0){
			List<Long> result=new ArrayList<Long>(values.length);
			if(ingoreEmpty){
				for(String value:values){
					if(!isEmpty(value)){
						result.add(parseLong(value,defaultValue));
					}
				}
			}else{
				for(String value:values){
					result.add(parseLong(value,defaultValue));
				}
			}
			if(result.isEmpty()) return null;
			return result;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Deprecated
	public static void debugParam(HttpServletRequest request) {
		System.out.println("****************** request.getParameterNames() start ******************");
		java.util.Enumeration<String> reqs=request.getParameterNames();
		String name=null;
		while(reqs.hasMoreElements()){
			name=reqs.nextElement();
			System.out.println(name + "	=	" + java.util.Arrays.toString(request.getParameterValues(name)));
		}
		System.out.println("****************** request.getParameterNames() end ******************");
	}
	
	@SuppressWarnings("unchecked")
	@Deprecated
	public static void debugAttr(HttpServletRequest request) {
		System.out.println("****************** request.getParameterNames() start ******************");
		java.util.Enumeration<String> reqs=request.getAttributeNames();
		String name=null;
		while(reqs.hasMoreElements()){
			name=reqs.nextElement();
			System.out.println(name + "	=	" + request.getAttribute(name));
		}
		System.out.println("****************** request.getParameterNames() end ******************");
	}
	
	@SuppressWarnings("unchecked")
	public static void debugHeader(HttpServletRequest request) {
		System.out.println("****************** request.getHeaderNames() start ******************");
		java.util.Enumeration<String> reqs=request.getHeaderNames();
		String name=null;
		while(reqs.hasMoreElements()){
			name=reqs.nextElement();
			System.out.println(name + "	=	" + StringUtil.enumeration2String(request.getHeaders(name)));
		}
		System.out.println("****************** request.getHeaderNames() end ******************");
	}
	
	@SuppressWarnings("unchecked")
	public static void debugSession(HttpServletRequest request) {
		System.out.println("****************** debugSession start ******************");
		HttpSession session=request.getSession(true);
		System.out.println("sessionid:" + session.getId());
		java.util.Enumeration<String> reqs=session.getAttributeNames();
		String name=null;
		while(reqs.hasMoreElements()){
			name=reqs.nextElement();
			System.out.println(name + "	=	" + session.getAttribute(name));
		}
		System.out.println("****************** debugSession end ******************");
	}
	
	public static void debug(HttpServletRequest request) {
		debugParam(request);
		debugAttr(request);
		debugHeader(request);
		debugSession(request);
	}
	
	public static void main(String[] args) {
		String[] is=new String[]{ "1", "2", "3", null, "" };
		System.out.println(getArrayIndexValue(is,10,"defaultValue"));
		System.out.println(parseInt("4.0000",0));
		System.out.println(parseLong("100000000000.99",0L));
	}
}
