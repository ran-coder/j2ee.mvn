package utils.servlet.httpproxy;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("rawtypes")
public class GetPost {
	@SuppressWarnings("unused")
	private String	NEWLINE	="\n";
	private int			errorCode;
	
	public GetPost() {
		errorCode=0;
		NEWLINE=System.getProperty("line.separator");
	}
	
	public void setErrorCode(int i) {
		errorCode=i;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public String doAction(String s, String s1, Hashtable hashtable, Hashtable hashtable1, int i, String s2, String s3, String s4, ServletInputStream servletinputstream,
			HttpServletResponse httpservletresponse) {
		if(i > 0){
			Properties properties=System.getProperties();
			properties.put("sun.net.client.defaultConnectTimeout",(new StringBuilder()).append("").append(i).toString());
			properties.put("sun.net.client.defaultReadTimeout",(new StringBuilder()).append("").append(i).toString());
			System.setProperties(properties);
		}
		if(s2 != null && s3 != null){
			Properties properties1=System.getProperties();
			properties1.put("proxySet","true");
			properties1.put("proxyHost",s2);
			properties1.put("proxyPort",s3);
			System.setProperties(properties1);
		}
		if("GET".equals(s.toUpperCase())){
			if(s1.toUpperCase().startsWith("HTTPS://")){
				return doGetSSL(s1,hashtable1,s4,httpservletresponse);
			}else{
				return doGet(s1,hashtable1,s4,httpservletresponse);
			}
		}
		if(s1.toUpperCase().startsWith("HTTPS://")){
			return doPostSSL(s1,hashtable,hashtable1,s4,servletinputstream,httpservletresponse);
		}else{
			return doPost(s1,hashtable,hashtable1,s4,servletinputstream,httpservletresponse);
		}
	}
	
	public String doDelete(String s, Hashtable hashtable, Hashtable hashtable1, int i, String s1, String s2, String s3, ServletInputStream servletinputstream,
			HttpServletResponse httpservletresponse) {
		if(i > 0){
			Properties properties=System.getProperties();
			properties.put("sun.net.client.defaultConnectTimeout",(new StringBuilder()).append("").append(i).toString());
			properties.put("sun.net.client.defaultReadTimeout",(new StringBuilder()).append("").append(i).toString());
			System.setProperties(properties);
		}
		if(s1 != null && s2 != null){
			Properties properties1=System.getProperties();
			properties1.put("proxySet","true");
			properties1.put("proxyHost",s1);
			properties1.put("proxyPort",s2);
			System.setProperties(properties1);
		}
		if(s.toUpperCase().startsWith("HTTPS://")){
			return doDeleteSSL(s,hashtable1,s3,httpservletresponse);
		}else{
			return doDelete(s,hashtable1,s3,httpservletresponse);
		}
	}
	
	public String doHead(String s, Hashtable hashtable, Hashtable hashtable1, int i, String s1, String s2, String s3, ServletInputStream servletinputstream,
			HttpServletResponse httpservletresponse) {
		if(i > 0){
			Properties properties=System.getProperties();
			properties.put("sun.net.client.defaultConnectTimeout",(new StringBuilder()).append("").append(i).toString());
			properties.put("sun.net.client.defaultReadTimeout",(new StringBuilder()).append("").append(i).toString());
			System.setProperties(properties);
		}
		if(s1 != null && s2 != null){
			Properties properties1=System.getProperties();
			properties1.put("proxySet","true");
			properties1.put("proxyHost",s1);
			properties1.put("proxyPort",s2);
			System.setProperties(properties1);
		}
		if(s.toUpperCase().startsWith("HTTPS://")){
			return doHeadSSL(s,hashtable1,s3,httpservletresponse);
		}else{
			return doHead(s,hashtable1,s3,httpservletresponse);
		}
	}
	
	public String doPut(String s, Hashtable hashtable, Hashtable hashtable1, int i, String s1, String s2, String s3, ServletInputStream servletinputstream,
			HttpServletResponse httpservletresponse) {
		if(i > 0){
			Properties properties=System.getProperties();
			properties.put("sun.net.client.defaultConnectTimeout",(new StringBuilder()).append("").append(i).toString());
			properties.put("sun.net.client.defaultReadTimeout",(new StringBuilder()).append("").append(i).toString());
			System.setProperties(properties);
		}
		if(s1 != null && s2 != null){
			Properties properties1=System.getProperties();
			properties1.put("proxySet","true");
			properties1.put("proxyHost",s1);
			properties1.put("proxyPort",s2);
			System.setProperties(properties1);
		}
		if(s.toUpperCase().startsWith("HTTPS://")){
			return doPutSSL(s,hashtable,hashtable1,s3,servletinputstream,httpservletresponse);
		}else{
			return doPut(s,hashtable,hashtable1,s3,servletinputstream,httpservletresponse);
		}
	}
	
	private String doPut(String s, Hashtable hashtable, Hashtable hashtable1, String s1, ServletInputStream servletinputstream, HttpServletResponse httpservletresponse) {
		HttpURLConnection httpurlconnection;
		httpurlconnection=null;
		String s2="";
		if(hashtable != null){
			s2=getParamsFromHash(hashtable);
		}
		try{
			URL url=new URL(s);
			httpurlconnection=(HttpURLConnection)url.openConnection();
			httpurlconnection.setRequestMethod("PUT");
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setDoInput(true);
			httpurlconnection.setUseCaches(false);
			httpurlconnection.setRequestProperty("content-type","application/x-www-form-urlencoded");
			if(hashtable1 != null){
				String s3;
				for(Enumeration enumeration=hashtable1.keys();enumeration.hasMoreElements();httpurlconnection.setRequestProperty(s3,(String)hashtable1.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
			PrintWriter printwriter=new PrintWriter(httpurlconnection.getOutputStream());
			printwriter.print(s2);
			int j;
			while((j=servletinputstream.read()) != -1){
				printwriter.write(j);
			}
			printwriter.close();
		}catch(Exception exception){
			getErrorCodeFromResponse(httpurlconnection);
			return getMessage(s,exception);
		}
		String s5;
		try{
			setErrorCode(httpurlconnection.getResponseCode());
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s4=httpurlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}
		if(hashtable1 != null){
			rewriteHeaders(httpurlconnection,httpservletresponse);
		}
		s5=httpurlconnection.getContentEncoding();
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(httpurlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(httpurlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	private String doPutSSL(String s, Hashtable hashtable, Hashtable hashtable1, String s1, ServletInputStream servletinputstream, HttpServletResponse httpservletresponse) {
		HttpsURLConnection httpsurlconnection;
		httpsurlconnection=null;
		String s2="";
		if(hashtable != null){
			s2=getParamsFromHash(hashtable);
		}
		try{
			URL url=new URL(s);
			httpsurlconnection=(HttpsURLConnection)url.openConnection();
			httpsurlconnection.setRequestMethod("PUT");
			httpsurlconnection.setDoOutput(true);
			httpsurlconnection.setDoInput(true);
			httpsurlconnection.setUseCaches(false);
			httpsurlconnection.setRequestProperty("content-type","application/x-www-form-urlencoded");
			if(hashtable1 != null){
				String s3;
				for(Enumeration enumeration=hashtable1.keys();enumeration.hasMoreElements();httpsurlconnection.setRequestProperty(s3,(String)hashtable1.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
			PrintWriter printwriter=new PrintWriter(httpsurlconnection.getOutputStream());
			printwriter.print(s2);
			int j;
			while((j=servletinputstream.read()) != -1){
				printwriter.write(j);
			}
			printwriter.close();
		}catch(Exception exception){
			getErrorCodeFromSSLResponse(httpsurlconnection);
			return getMessage(s,exception);
		}
		String s5;
		if(httpsurlconnection instanceof HttpURLConnection){
			HttpsURLConnection httpsurlconnection1=httpsurlconnection;
			try{
				setErrorCode(httpsurlconnection1.getResponseCode());
			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String s4=httpsurlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}
		s5=httpsurlconnection.getContentEncoding();
		if(hashtable1 != null){
			rewriteSSLHeaders(httpsurlconnection,httpservletresponse);
		}
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(httpsurlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(httpsurlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	private String doPost(String s, Hashtable hashtable, Hashtable hashtable1, String s1, ServletInputStream servletinputstream, HttpServletResponse httpservletresponse) {
		URLConnection urlconnection;
		urlconnection=null;
		String s2="";
		if(hashtable != null){
			s2=getParamsFromHash(hashtable);
		}
		try{
			URL url=new URL(s);
			urlconnection=url.openConnection();
			urlconnection.setDoOutput(true);
			urlconnection.setDoInput(true);
			urlconnection.setUseCaches(false);
			urlconnection.setRequestProperty("content-type","application/x-www-form-urlencoded");
			if(hashtable1 != null){
				String s3;
				for(Enumeration enumeration=hashtable1.keys();enumeration.hasMoreElements();urlconnection.setRequestProperty(s3,(String)hashtable1.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
			PrintWriter printwriter=new PrintWriter(urlconnection.getOutputStream());
			printwriter.print(s2);
			int j;
			while((j=servletinputstream.read()) != -1){
				printwriter.write(j);
			}
			printwriter.close();
		}catch(Exception exception){
			getErrorCodeFromResponse(urlconnection);
			return getMessage(s,exception);
		}
		String s5;
		if(urlconnection instanceof HttpURLConnection){
			HttpURLConnection httpurlconnection=(HttpURLConnection)urlconnection;
			try{
				setErrorCode(httpurlconnection.getResponseCode());
			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String s4=urlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}
		if(hashtable1 != null){
			rewriteHeaders(urlconnection,httpservletresponse);
		}
		s5=urlconnection.getContentEncoding();
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(urlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(urlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	private String doPostSSL(String s, Hashtable hashtable, Hashtable hashtable1, String s1, ServletInputStream servletinputstream, HttpServletResponse httpservletresponse) {
		HttpsURLConnection httpsurlconnection;
		httpsurlconnection=null;
		String s2="";
		if(hashtable != null){
			s2=getParamsFromHash(hashtable);
		}
		try{
			URL url=new URL(s);
			httpsurlconnection=(HttpsURLConnection)url.openConnection();
			httpsurlconnection.setDoOutput(true);
			httpsurlconnection.setDoInput(true);
			httpsurlconnection.setUseCaches(false);
			httpsurlconnection.setRequestProperty("content-type","application/x-www-form-urlencoded");
			if(hashtable1 != null){
				String s3;
				for(Enumeration enumeration=hashtable1.keys();enumeration.hasMoreElements();httpsurlconnection.setRequestProperty(s3,(String)hashtable1.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
			PrintWriter printwriter=new PrintWriter(httpsurlconnection.getOutputStream());
			printwriter.print(s2);
			int j;
			while((j=servletinputstream.read()) != -1){
				printwriter.write(j);
			}
			printwriter.close();
		}catch(Exception exception){
			getErrorCodeFromSSLResponse(httpsurlconnection);
			return getMessage(s,exception);
		}
		String s5;
		if(httpsurlconnection instanceof HttpURLConnection){
			HttpsURLConnection httpsurlconnection1=httpsurlconnection;
			try{
				setErrorCode(httpsurlconnection1.getResponseCode());
			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String s4=httpsurlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}
		s5=httpsurlconnection.getContentEncoding();
		if(hashtable1 != null){
			rewriteSSLHeaders(httpsurlconnection,httpservletresponse);
		}
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(httpsurlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(httpsurlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	private String doGet(String s, Hashtable hashtable, String s1, HttpServletResponse httpservletresponse) {
		URLConnection urlconnection;
		urlconnection=null;
		try{
			URL url=new URL(s);
			urlconnection=url.openConnection();
			urlconnection.setDoInput(true);
			urlconnection.setUseCaches(false);
			if(hashtable != null){
				String s3;
				for(Enumeration enumeration=hashtable.keys();enumeration.hasMoreElements();urlconnection.setRequestProperty(s3,(String)hashtable.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
		}catch(Exception exception){
			getErrorCodeFromResponse(urlconnection);
			return getMessage(s,exception);
		}
		String s5;
		if(urlconnection instanceof HttpURLConnection){
			HttpURLConnection httpurlconnection=(HttpURLConnection)urlconnection;
			try{
				setErrorCode(httpurlconnection.getResponseCode());
			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String s4=urlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}else{
			s4="";
		}
		s5=urlconnection.getContentEncoding();
		if(hashtable != null){
			rewriteHeaders(urlconnection,httpservletresponse);
		}
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(urlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(urlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	private String doGetSSL(String s, Hashtable hashtable, String s1, HttpServletResponse httpservletresponse) {
		HttpsURLConnection httpsurlconnection;
		httpsurlconnection=null;
		try{
			URL url=new URL(s);
			httpsurlconnection=(HttpsURLConnection)url.openConnection();
			httpsurlconnection.setDoInput(true);
			httpsurlconnection.setUseCaches(false);
			if(hashtable != null){
				String s3;
				for(Enumeration enumeration=hashtable.keys();enumeration.hasMoreElements();httpsurlconnection.setRequestProperty(s3,(String)hashtable.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
		}catch(Exception exception){
			getErrorCodeFromSSLResponse(httpsurlconnection);
			return getMessage(s,exception);
		}
		String s5;
		if(httpsurlconnection instanceof HttpURLConnection){
			HttpsURLConnection httpsurlconnection1=httpsurlconnection;
			try{
				setErrorCode(httpsurlconnection1.getResponseCode());
			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String s4=httpsurlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}else{
			s4="";
		}
		s5=httpsurlconnection.getContentEncoding();
		if(hashtable != null){
			rewriteSSLHeaders(httpsurlconnection,httpservletresponse);
		}
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(httpsurlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(httpsurlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	private String doDelete(String s, Hashtable hashtable, String s1, HttpServletResponse httpservletresponse) {
		HttpURLConnection httpurlconnection;
		httpurlconnection=null;
		try{
			URL url=new URL(s);
			httpurlconnection=(HttpURLConnection)url.openConnection();
			httpurlconnection.setRequestMethod("DELETE");
			httpurlconnection.setDoInput(true);
			httpurlconnection.setUseCaches(false);
			if(hashtable != null){
				String s3;
				for(Enumeration enumeration=hashtable.keys();enumeration.hasMoreElements();httpurlconnection.setRequestProperty(s3,(String)hashtable.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
		}catch(Exception exception){
			getErrorCodeFromResponse(httpurlconnection);
			return getMessage(s,exception);
		}
		String s5;
		try{
			setErrorCode(httpurlconnection.getResponseCode());
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s4=httpurlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}else{
			s4="";
		}
		s5=httpurlconnection.getContentEncoding();
		if(hashtable != null){
			rewriteHeaders(httpurlconnection,httpservletresponse);
		}
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(httpurlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(httpurlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	private String doDeleteSSL(String s, Hashtable hashtable, String s1, HttpServletResponse httpservletresponse) {
		HttpsURLConnection httpsurlconnection;
		httpsurlconnection=null;
		try{
			URL url=new URL(s);
			httpsurlconnection=(HttpsURLConnection)url.openConnection();
			httpsurlconnection.setRequestMethod("DELETE");
			httpsurlconnection.setDoInput(true);
			httpsurlconnection.setUseCaches(false);
			if(hashtable != null){
				String s3;
				for(Enumeration enumeration=hashtable.keys();enumeration.hasMoreElements();httpsurlconnection.setRequestProperty(s3,(String)hashtable.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
		}catch(Exception exception){
			getErrorCodeFromSSLResponse(httpsurlconnection);
			return getMessage(s,exception);
		}
		String s5;
		try{
			setErrorCode(httpsurlconnection.getResponseCode());
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s4=httpsurlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}else{
			s4="";
		}
		s5=httpsurlconnection.getContentEncoding();
		if(hashtable != null){
			rewriteHeaders(httpsurlconnection,httpservletresponse);
		}
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(httpsurlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(httpsurlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	private String doHead(String s, Hashtable hashtable, String s1, HttpServletResponse httpservletresponse) {
		HttpURLConnection httpurlconnection;
		httpurlconnection=null;
		try{
			URL url=new URL(s);
			httpurlconnection=(HttpURLConnection)url.openConnection();
			httpurlconnection.setRequestMethod("HEAD");
			httpurlconnection.setDoInput(true);
			httpurlconnection.setUseCaches(false);
			if(hashtable != null){
				String s3;
				for(Enumeration enumeration=hashtable.keys();enumeration.hasMoreElements();httpurlconnection.setRequestProperty(s3,(String)hashtable.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
		}catch(Exception exception){
			getErrorCodeFromResponse(httpurlconnection);
			return getMessage(s,exception);
		}
		String s5;
		try{
			setErrorCode(httpurlconnection.getResponseCode());
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s4=httpurlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}else{
			s4="";
		}
		s5=httpurlconnection.getContentEncoding();
		if(hashtable != null){
			rewriteHeaders(httpurlconnection,httpservletresponse);
		}
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(httpurlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(httpurlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	private String doHeadSSL(String s, Hashtable hashtable, String s1, HttpServletResponse httpservletresponse) {
		HttpsURLConnection httpsurlconnection;
		httpsurlconnection=null;
		try{
			URL url=new URL(s);
			httpsurlconnection=(HttpsURLConnection)url.openConnection();
			httpsurlconnection.setRequestMethod("HEAD");
			httpsurlconnection.setDoInput(true);
			httpsurlconnection.setUseCaches(false);
			if(hashtable != null){
				String s3;
				for(Enumeration enumeration=hashtable.keys();enumeration.hasMoreElements();httpsurlconnection.setRequestProperty(s3,(String)hashtable.get(s3))){
					s3=(String)enumeration.nextElement();
				}
				
			}
		}catch(Exception exception){
			getErrorCodeFromSSLResponse(httpsurlconnection);
			return getMessage(s,exception);
		}
		String s5;
		try{
			setErrorCode(httpsurlconnection.getResponseCode());
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s4=httpsurlconnection.getContentType();
		if(s4 != null && s1 != null){
			s4=rewriteEncoding(s4,s1);
		}
		if(s4 != null){
			httpservletresponse.setContentType(s4);
		}else{
			s4="";
		}
		s5=httpsurlconnection.getContentEncoding();
		if(hashtable != null){
			rewriteHeaders(httpsurlconnection,httpservletresponse);
		}
		if(s5 == null){
			s5="";
		}
		Object obj2;
		BufferedOutputStream bufferedoutputstream;
		try{
			if(s5.indexOf("gzip") >= 0){
				obj2=new GZIPInputStream(httpsurlconnection.getInputStream());
			}else{
				obj2=new BufferedInputStream(httpsurlconnection.getInputStream());
			}
			bufferedoutputstream=new BufferedOutputStream(httpservletresponse.getOutputStream());
			int i;
			while((i=((InputStream)(obj2)).read()) >= 0){
				bufferedoutputstream.write(i);
			}
		}catch(Exception exception2){
			return getMessage(s,exception2);
		}
		try{
			if(obj2 != null){
				((InputStream)(obj2)).close();
			}
			if(bufferedoutputstream != null){
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
		}catch(Exception exception1){
			return getMessage(s,exception1);
		}
		return null;
	}
	
	protected String getMessage(String s, Exception exception) {
		String s1=exception.getClass().getName();
		int i=s1.lastIndexOf('.');
		s1=s1.substring(i + 1);
		StringWriter stringwriter=new StringWriter();
		PrintWriter printwriter=new PrintWriter(stringwriter);
		exception.printStackTrace(printwriter);
		printwriter.close();
		return (new StringBuilder()).append("Request: ").append(s).append("\nException: ").append(s1).append(": ").append(exception.getMessage()).append("\n")
				.append(stringwriter.getBuffer().toString()).toString();
	}
	
	@SuppressWarnings("unused")
	private InputStreamReader getInputStreamReader(InputStream inputstream, String s) throws UnsupportedEncodingException {
		if(s == null){
			return new InputStreamReader(inputstream);
		}else{
			return new InputStreamReader(inputstream,s);
		}
	}
	
	private String rewriteEncoding(String s, String s1) {
		if(s.indexOf("charset") < 0){ return (new StringBuilder()).append(s).append(";charset=").append(s1).toString(); }
		int i=s.indexOf(";");
		if(i < 0){
			i=s.indexOf("charset");
		}
		return (new StringBuilder()).append(s.substring(0,i)).append(";charset=").append(s1).toString();
	}
	
	@SuppressWarnings("deprecation")
	private String getParamsFromHash(Hashtable hashtable) {
		String s="";
		for(Enumeration enumeration=hashtable.keys();enumeration.hasMoreElements();){
			if(s.length() > 0){
				s=(new StringBuilder()).append(s).append("&").toString();
			}
			String s2=(String)enumeration.nextElement();
			String s3=s2;
			int i=s3.indexOf("<");
			if(i > 0){
				s3=s3.substring(0,i);
			}
			s=(new StringBuilder()).append(s).append(s3).append("=").toString();
			s=(new StringBuilder()).append(s).append(URLEncoder.encode((String)hashtable.get(s2))).toString();
		}
		
		return s;
	}
	
	private void rewriteHeaders(URLConnection urlconnection, HttpServletResponse httpservletresponse) {
		Map map=urlconnection.getHeaderFields();
		if(map != null){
			Set set=map.keySet();
			Iterator iterator=set.iterator();
			do{
				if(!iterator.hasNext()){
					break;
				}
				String s=(String)iterator.next();
				if(s != null && !s.equals("Content-Type")){
					List list=(List)map.get(s);
					String s1="";
					for(Iterator iterator1=list.iterator();iterator1.hasNext();){
						if(s1.length() > 0){
							s1=(new StringBuilder()).append(s1).append(",").toString();
						}
						s1=(new StringBuilder()).append(s1).append((String)iterator1.next()).toString();
					}
					
					httpservletresponse.setHeader(s,s1);
				}
			}while(true);
		}
	}
	
	private void rewriteSSLHeaders(HttpsURLConnection httpsurlconnection, HttpServletResponse httpservletresponse) {
		Map map=httpsurlconnection.getHeaderFields();
		if(map != null){
			Set set=map.keySet();
			Iterator iterator=set.iterator();
			do{
				if(!iterator.hasNext()){
					break;
				}
				String s=(String)iterator.next();
				if(s != null && !s.equals("Content-Type")){
					List list=(List)map.get(s);
					String s1="";
					for(Iterator iterator1=list.iterator();iterator1.hasNext();){
						if(s1.length() > 0){
							s1=(new StringBuilder()).append(s1).append(",").toString();
						}
						s1=(new StringBuilder()).append(s1).append((String)iterator1.next()).toString();
					}
					
					httpservletresponse.setHeader(s,s1);
				}
			}while(true);
		}
	}
	
	private void getErrorCodeFromResponse(URLConnection urlconnection) {
		try{
			if(urlconnection instanceof HttpURLConnection){
				getErrorCodeFromResponse((HttpURLConnection)urlconnection);
			}
		}catch(Exception exception){
			setErrorCode(500);
		}
	}
	
	private void getErrorCodeFromResponse(HttpURLConnection httpurlconnection) {
		try{
			setErrorCode(httpurlconnection.getResponseCode());
		}catch(Exception exception){
			setErrorCode(500);
		}
	}
	
	@SuppressWarnings("unused")
	private void getErrorCodeFromSSLResponse(URLConnection urlconnection) {
		try{
			if(urlconnection instanceof HttpsURLConnection){
				getErrorCodeFromSSLResponse((HttpsURLConnection)urlconnection);
			}
		}catch(Exception exception){
			setErrorCode(500);
		}
	}
	
	private void getErrorCodeFromSSLResponse(HttpsURLConnection httpsurlconnection) {
		try{
			setErrorCode(httpsurlconnection.getResponseCode());
		}catch(Exception exception){
			setErrorCode(500);
		}
	}
}
