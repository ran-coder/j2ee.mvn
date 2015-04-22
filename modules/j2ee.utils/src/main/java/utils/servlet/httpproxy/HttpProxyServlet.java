package utils.servlet.httpproxy;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.jsos.httpproxy:
// GetPost
@SuppressWarnings({"rawtypes","unchecked","unused"})
public class HttpProxyServlet extends HttpServlet {
	private static final long	serialVersionUID	=6211942087999645101L;
	private String						host;
	private String						proxyHost;
	private String						proxyPort;
	private String						rewriteHost;
	private String						encoding;
	private String						keepHeaders;
	private String						uri;
	private String						path;
	private String						disableAcceptEncoding;
	private String						log;
	private String						certificate;
	
	public HttpProxyServlet() {
		keepHeaders="true";
		path="false";
		disableAcceptEncoding="false";
		log="false";
		certificate="false";
	}
	
	public void init() throws ServletException {
		host=getInitParameter("host");
		if(host == null){
			System.out.println("HttpProxyServlet needs a host in the parameter.");
			throw new ServletException("HttpProxyServlet needs a host in the parameter.");
		}
		proxyHost=getInitParameter("proxyHost");
		proxyPort=getInitParameter("proxyPort");
		rewriteHost=getInitParameter("rewriteHost");
		encoding=getInitParameter("encoding");
		uri=getInitParameter("uri");
		path=getInitParameter("path");
		keepHeaders=getInitParameter("headers");
		disableAcceptEncoding=getInitParameter("gzip");
		log=getInitParameter("log");
		certificate=getInitParameter("certificate");
		if(keepHeaders == null){
			keepHeaders="true";
		}
		if(log == null){
			log="false";
		}
		if(certificate == null){
			certificate="false";
		}
		if(disableAcceptEncoding == null){
			disableAcceptEncoding="false";
		}
		if("true".equals(log)){
			System.out.println("HttpProxy servlet. Init");
		}
		if("true".equals(certificate)){
			TrustManager atrustmanager[]={ new ProxyX509TrustManager(this) };
			try{
				SSLContext sslcontext=SSLContext.getInstance("SSL");
				sslcontext.init(null,atrustmanager,new SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
			}catch(Exception exception){
			}
		}
	}
	

	public void doDelete(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException {
		String s=httpservletrequest.getQueryString();
		String s1=host;
		if(uri != null){
			uri=normalizeUri(uri);
			String s2=httpservletrequest.getRequestURI();
			if(s2 != null){
				if(!s2.startsWith(uri)){
					s2=null;
				}else if(uri.length() >= s2.length()){
					s2=null;
				}else{
					s2=s2.substring(uri.length());
				}
				if(s2 != null){
					s1=addUri(s1,s2);
				}
			}
		}
		if("true".equals(path)){
			s1=addUri(s1,httpservletrequest.getPathInfo());
		}
		s1=addQuery(s1,s);
		if("true".equals(log)){
			System.out.println((new StringBuilder()).append("HTTP DELETE. url=").append(s1).toString());
		}
		Hashtable hashtable=null;
		if("true".equals(keepHeaders)){
			hashtable=new Hashtable();
			getHeaders(httpservletrequest,hashtable);
			if("true".equals(rewriteHost)){
				hashtable.remove("host");
				hashtable.remove("Host");
				hashtable.remove("HOST");
				hashtable.put("Host",getHostInfo(host));
			}
			if("false".equals(disableAcceptEncoding)){
				hashtable.remove("ACCEPT-ENCODING");
			}
		}
		GetPost getpost=new GetPost();
		String s3=getpost.doDelete(s1,null,hashtable,-1,proxyHost,proxyPort,encoding,httpservletrequest.getInputStream(),httpservletresponse);
		if(s3 != null){
			int i=getpost.getErrorCode();
			if(i > 0){
				httpservletresponse.sendError(i,s3);
			}else{
				httpservletresponse.sendError(503,s3);
			}
			if("true".equals(log)){
				System.out.println((new StringBuilder()).append("Error:").append(s3).toString());
			}
			if("true".equals(log)){
				System.out.println((new StringBuilder()).append("Error code:").append(i).toString());
			}
		}
	}
	

	public void doHead(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException {
		String s=httpservletrequest.getQueryString();
		String s1=host;
		if(uri != null){
			uri=normalizeUri(uri);
			String s2=httpservletrequest.getRequestURI();
			if(s2 != null){
				if(!s2.startsWith(uri)){
					s2=null;
				}else if(uri.length() >= s2.length()){
					s2=null;
				}else{
					s2=s2.substring(uri.length());
				}
				if(s2 != null){
					s1=addUri(s1,s2);
				}
			}
		}
		if("true".equals(path)){
			s1=addUri(s1,httpservletrequest.getPathInfo());
		}
		s1=addQuery(s1,s);
		if("true".equals(log)){
			System.out.println((new StringBuilder()).append("HTTP HEAD. url=").append(s1).toString());
		}
		Hashtable hashtable=null;
		if("true".equals(keepHeaders)){
			hashtable=new Hashtable();
			getHeaders(httpservletrequest,hashtable);
			if("true".equals(rewriteHost)){
				hashtable.remove("host");
				hashtable.remove("Host");
				hashtable.remove("HOST");
				hashtable.put("Host",getHostInfo(host));
			}
			if("false".equals(disableAcceptEncoding)){
				hashtable.remove("ACCEPT-ENCODING");
			}
		}
		GetPost getpost=new GetPost();
		String s3=getpost.doHead(s1,null,hashtable,-1,proxyHost,proxyPort,encoding,httpservletrequest.getInputStream(),httpservletresponse);
		if(s3 != null){
			int i=getpost.getErrorCode();
			if(i > 0){
				httpservletresponse.sendError(i,s3);
			}else{
				httpservletresponse.sendError(503,s3);
			}
			if("true".equals(log)){
				System.out.println((new StringBuilder()).append("Error:").append(s3).toString());
			}
			if("true".equals(log)){
				System.out.println((new StringBuilder()).append("Error code:").append(i).toString());
			}
		}
	}
	

	public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException {
		String s=httpservletrequest.getQueryString();
		String s1=host;
		Object obj=null;
		if(uri != null){
			uri=normalizeUri(uri);
			String s2=httpservletrequest.getRequestURI();
			if(s2 != null){
				if(!s2.startsWith(uri)){
					s2=null;
				}else if(uri.length() >= s2.length()){
					s2=null;
				}else{
					s2=s2.substring(uri.length());
				}
				if(s2 != null){
					s1=addUri(s1,s2);
				}
			}
		}
		if("true".equals(path)){
			s1=addUri(s1,httpservletrequest.getPathInfo());
		}
		s1=addQuery(s1,s);
		if("true".equals(log)){
			System.out.println((new StringBuilder()).append("HTTP GET. url=").append(s1).toString());
		}
		Hashtable hashtable=null;
		if("true".equals(keepHeaders)){
			hashtable=new Hashtable();
			getHeaders(httpservletrequest,hashtable);
			if("true".equals(rewriteHost)){
				hashtable.remove("host");
				hashtable.remove("Host");
				hashtable.remove("HOST");
				hashtable.put("Host",getHostInfo(host));
			}
			if("false".equals(disableAcceptEncoding)){
				hashtable.remove("ACCEPT-ENCODING");
			}
		}
		GetPost getpost=new GetPost();
		String s3=getpost.doAction(httpservletrequest.getMethod().toUpperCase(),s1,null,hashtable,-1,proxyHost,proxyPort,encoding,httpservletrequest.getInputStream(),
				httpservletresponse);
		if(s3 != null){
			int i=getpost.getErrorCode();
			if(i > 0){
				httpservletresponse.sendError(i,s3);
			}else{
				httpservletresponse.sendError(503,s3);
			}
			if("true".equals(log)){
				System.out.println((new StringBuilder()).append("Error:").append(s3).toString());
			}
			if("true".equals(log)){
				System.out.println((new StringBuilder()).append("Error code:").append(i).toString());
			}
		}
	}
	
	public void doPost(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException {
		String s=httpservletrequest.getQueryString();
		String s1=host;
		Object obj=null;
		if(uri != null){
			uri=normalizeUri(uri);
			String s2=httpservletrequest.getRequestURI();
			if(s2 != null){
				if(!s2.startsWith(uri)){
					s2=null;
				}else if(uri.length() >= s2.length()){
					s2=null;
				}else{
					s2=s2.substring(uri.length());
				}
				if(s2 != null){
					s1=addUri(s1,s2);
				}
			}
		}
		if("true".equals(path)){
			s1=addUri(s1,httpservletrequest.getPathInfo());
		}
		s1=addQuery(s1,s);
		if("true".equals(log)){
			System.out.println((new StringBuilder()).append("HTTP POST. url=").append(s1).toString());
		}
		Hashtable hashtable=null;
		Hashtable hashtable1=new Hashtable();
		Enumeration enumeration=httpservletrequest.getParameterNames();
		do{
			if(!enumeration.hasMoreElements()){
				break;
			}
			String s3=(String)enumeration.nextElement();
			String as[]=httpservletrequest.getParameterValues(s3);
			if(as != null && as.length > 0){
				int i=0;
				while(i < as.length){
					hashtable1.put((new StringBuilder()).append(s3).append("<").append(i).append(">").toString(),as[i]);
					i++;
				}
			}
		}while(true);
		if("true".equals(keepHeaders)){
			hashtable=new Hashtable();
			getHeaders(httpservletrequest,hashtable);
			if("true".equals(rewriteHost)){
				hashtable.remove("host");
				hashtable.remove("Host");
				hashtable.remove("HOST");
				hashtable.put("Host",getHostInfo(host));
			}
			if("false".equals(disableAcceptEncoding)){
				hashtable.remove("ACCEPT-ENCODING");
			}
		}
		GetPost getpost=new GetPost();
		String s4=getpost.doAction(httpservletrequest.getMethod().toUpperCase(),s1,hashtable1,hashtable,-1,proxyHost,proxyPort,encoding,httpservletrequest.getInputStream(),
				httpservletresponse);
		if(s4 != null){
			int j=getpost.getErrorCode();
			if(j > 0){
				httpservletresponse.sendError(j,s4);
			}else{
				httpservletresponse.sendError(503,s4);
			}
			if("true".equals(log)){
				System.out.println((new StringBuilder()).append("Error:").append(s4).toString());
			}
			if("true".equals(log)){
				System.out.println((new StringBuilder()).append("Error code:").append(j).toString());
			}
		}
	}
	
	public void doPut(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException {
		String s=httpservletrequest.getQueryString();
		String s1=host;
		Object obj=null;
		if(uri != null){
			uri=normalizeUri(uri);
			String s2=httpservletrequest.getRequestURI();
			if(s2 != null){
				if(!s2.startsWith(uri)){
					s2=null;
				}else if(uri.length() >= s2.length()){
					s2=null;
				}else{
					s2=s2.substring(uri.length());
				}
				if(s2 != null){
					s1=addUri(s1,s2);
				}
			}
		}
		s1=addQuery(s1,s);
		if("true".equals(log)){
			System.out.println((new StringBuilder()).append("HTTP PUT. url=").append(s1).toString());
		}
		Hashtable hashtable=null;
		Hashtable hashtable1=new Hashtable();
		Enumeration enumeration=httpservletrequest.getParameterNames();
		do{
			if(!enumeration.hasMoreElements()){
				break;
			}
			String s3=(String)enumeration.nextElement();
			String as[]=httpservletrequest.getParameterValues(s3);
			if(as != null && as.length > 0){
				int i=0;
				while(i < as.length){
					hashtable1.put((new StringBuilder()).append(s3).append("<").append(i).append(">").toString(),as[i]);
					i++;
				}
			}
		}while(true);
		if("true".equals(keepHeaders)){
			hashtable=new Hashtable();
			getHeaders(httpservletrequest,hashtable);
			if("true".equals(rewriteHost)){
				hashtable.remove("host");
				hashtable.remove("Host");
				hashtable.remove("HOST");
				hashtable.put("Host",getHostInfo(host));
			}
			if("false".equals(disableAcceptEncoding)){
				hashtable.remove("ACCEPT-ENCODING");
			}
		}
		GetPost getpost=new GetPost();
		String s4=getpost.doPut(s1,hashtable1,hashtable,-1,proxyHost,proxyPort,encoding,httpservletrequest.getInputStream(),httpservletresponse);
		if(s4 != null){
			int j=getpost.getErrorCode();
			if(j > 0){
				httpservletresponse.sendError(j,s4);
			}else{
				httpservletresponse.sendError(503,s4);
			}
		}
	}
	
	private void getHeaders(HttpServletRequest httpservletrequest, Hashtable hashtable) {
		String s;
		String s1;
		for(Enumeration enumeration=httpservletrequest.getHeaderNames();enumeration.hasMoreElements();hashtable.put(s,s1)){
			s=(String)enumeration.nextElement();
			s1="";
			for(Enumeration enumeration1=httpservletrequest.getHeaders(s);enumeration1.hasMoreElements();){
				if(s1.length() > 0){
					s1=(new StringBuilder()).append(s1).append(",").toString();
				}
				s1=(new StringBuilder()).append(s1).append((String)enumeration1.nextElement()).toString();
			}
			
		}
		
	}
	
	private String getHostInfo(String s) {
		String s1=s;
		int i=s1.indexOf("://");
		if(i > 0){
			s1=s1.substring(i + 3);
		}
		i=s1.indexOf("/");
		if(i > 0){
			s1=s1.substring(0,i);
		}
		i=s1.indexOf("?");
		if(i > 0){
			s1=s1.substring(0,i);
		}
		i=s1.indexOf("#");
		if(i > 0){
			s1=s1.substring(0,i);
		}
		i=s1.indexOf(";");
		if(i > 0){
			s1=s1.substring(0,i);
		}
		return s1;
	}
	
	private String addQuery(String s, String s1) {
		if(s1 != null){
			if(s.indexOf("?") < 0){
				return (new StringBuilder()).append(s).append("?").append(s1).toString();
			}else{
				return (new StringBuilder()).append(s).append("&").append(s1).toString();
			}
		}else{
			return s;
		}
	}
	
	private String addUri(String s, String s1) {
		if(s1 == null){ return s; }
		if(s1.length() == 0){ return s; }
		if(s1.equals("/")){ return s; }
		String s2=s1;
		if(s2.charAt(0) == '/'){
			s2=s2.substring(1);
		}
		if(s2.length() == 0){ return s; }
		String s3="";
		String s4=s;
		int i=s4.indexOf("?");
		if(i > 0){
			if(i < s4.length() - 1){
				s3=s4.substring(i + 1);
			}
			s4=s4.substring(0,i);
		}
		if(!s4.endsWith("/")){
			s4=(new StringBuilder()).append(s4).append("/").toString();
		}
		s4=(new StringBuilder()).append(s4).append(s2).toString();
		if(s3.length() > 0){
			s4=(new StringBuilder()).append(s4).append("?").append(s3).toString();
		}
		return s4;
	}
	
	private String normalizeUri(String s) {
		int i=s.indexOf("*");
		if(i < 0){ return s; }
		if(i == 0){
			return "/";
		}else{
			return s.substring(0,i);
		}
	}
}
