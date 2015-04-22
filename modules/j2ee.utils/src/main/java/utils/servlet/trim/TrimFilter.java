package utils.servlet.trim;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.cj.trim:
// trimResponseWrapper

public class TrimFilter implements Filter {
	
	private FilterConfig				config;
	private boolean							no_init;
	private boolean							removeComments;
	private String							excluded;
	private Pattern							pExcluded;
	static final String	TEMPLATE				="TEXT/";
	static final String	REMOVECOMMENTS	="removeComments";
	static final String	EXCLUDE					="exclude";
	static final String	EXPIRES					="expires";
	
	public TrimFilter() {
		no_init=true;
		removeComments=false;
	}
	
	public void init(FilterConfig filterconfig) throws ServletException {
		no_init=false;
		config=filterconfig;
		if((excluded=filterconfig.getInitParameter("exclude")) != null){
			pExcluded=Pattern.compile(excluded);
		}
		String s=filterconfig.getInitParameter("removeComments");
		if(s != null && "TRUE".equals(s.toUpperCase())){
			removeComments=true;
		}
	}
	
	public void destroy() {
		config=null;
	}
	
	public FilterConfig getFilterConfig() {
		return config;
	}
	
	public void setFilterConfig(FilterConfig filterconfig) {
		if(no_init){
			no_init=false;
			config=filterconfig;
			if((excluded=filterconfig.getInitParameter("exclude")) != null){
				pExcluded=Pattern.compile(excluded);
			}
			String s=filterconfig.getInitParameter("removeComments");
			if(s != null && "TRUE".equals(s.toUpperCase())){
				removeComments=true;
			}
		}
	}
	
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain) throws IOException, ServletException {
		String s=((HttpServletRequest)servletrequest).getRequestURI();
		TrimResponseWrapper trimresponsewrapper=new TrimResponseWrapper((HttpServletResponse)servletresponse);
		int i=0;
		if(excluded(s)){
			filterchain.doFilter(servletrequest,servletresponse);
		}else{
			filterchain.doFilter(servletrequest,trimresponsewrapper);
			byte abyte0[]=trimresponsewrapper.getData();
			if(abyte0 == null){ return; }
			byte abyte1[]=null;
			if(abyte0.length > 0){
				abyte1=new byte[abyte0.length];
			}else{
				return;
			}
			String s1=trimresponsewrapper.getContentType();
			javax.servlet.ServletOutputStream servletoutputstream=null;
			if(s1 != null){
				servletresponse.setContentType(s1);
				String s2=config.getInitParameter("expires");
				if(s2 != null){
					long l=-1L;
					try{
						l=Long.parseLong(s2);
					}catch(Exception exception){
						l=-1L;
					}
					if(l > 0L){
						Date date=new Date();
						((HttpServletResponse)servletresponse).setDateHeader("Expires",date.getTime() + l * 1000L);
						((HttpServletResponse)servletresponse).setHeader("Cache-Control",(new StringBuilder()).append("max-age=").append(l).toString());
					}
				}
				servletoutputstream=servletresponse.getOutputStream();
				if(s1.toUpperCase().indexOf("TEXT/") >= 0){
					//boolean flag=false;
					int j=0;
					char c1=' ';
					label0: do{
						if(j >= abyte0.length){
							break;
						}
						byte byte2=abyte0[j];
						char c;
						if((c=(char)abyte0[j]) == '\r'){
							c='\n';
							byte2=10;
						}
						if(removeComments && c == '<' && j + 3 < abyte0.length && abyte0[j + 1] == 33 && abyte0[j + 2] == 45){
							int k=j;
							j+=2;
							do{
								if(j >= abyte0.length){
									break;
								}
								c=(char)abyte0[j];
								byte2=abyte0[j];
								if(c == '>' && abyte0[j - 1] == 45 && abyte0[j - 2] == 45){
									if(j - 4 >= 0 && abyte0[j - 3] == 47 && abyte0[j - 4] == 47){
										abyte1[i]=60;
										i++;
										j=k;
										c1='<';
									}else{
										c1=' ';
									}
									break;
								}
								j++;
							}while(true);
							j++;
							continue;
						}
						if(c == '<' && isToken(abyte0,j,"pre")){
							for(int i1=j;i1 <= j + 3;i1++){
								abyte1[i]=abyte0[i1];
								i++;
							}
							
							j+=4;
							do{
								if(j >= abyte0.length){
									continue label0;
								}
								byte byte0=abyte0[j];
								if(byte0 != 60){
									abyte1[i]=byte0;
									i++;
								}else{
									if(isToken(abyte0,j,"/pre>")){
										for(int j1=j;j1 <= j + 5;j1++){
											abyte1[i]=abyte0[j1];
											i++;
										}
										
										j+=6;
										continue label0;
									}
									abyte1[i]=byte0;
									i++;
								}
								j++;
							}while(true);
						}
						if(c == '<' && isToken(abyte0,j,"textarea")){
							for(int k1=j;k1 <= j + 8;k1++){
								abyte1[i]=abyte0[k1];
								i++;
							}
							
							j+=9;
							do{
								if(j >= abyte0.length){
									continue label0;
								}
								byte byte1=abyte0[j];
								if(byte1 != 60){
									abyte1[i]=byte1;
									i++;
								}else{
									if(isToken(abyte0,j,"/textarea>")){
										for(int l1=j;l1 <= j + 10;l1++){
											abyte1[i]=abyte0[l1];
											i++;
										}
										
										j+=11;
										continue label0;
									}
									abyte1[i]=byte1;
									i++;
								}
								j++;
							}while(true);
						}
						if(c1 == 10){
							if(c == '\n'){
								j++;
								continue;
							}
							if(c == ' '){
								j++;
								continue;
							}
							if(c == '\t'){
								j++;
								continue;
							}
						}
						if((c1 == 32 || c1 == 9) && (c == ' ' || c == '\t')){
							j++;
						}else{
							c1=c;
							abyte1[i]=byte2;
							i++;
							j++;
						}
					}while(true);
					servletresponse.setContentLength(i);
					servletoutputstream.write(abyte1,0,i);
				}else{
					servletoutputstream.write(abyte0);
				}
			}else{
				servletoutputstream=servletresponse.getOutputStream();
				servletoutputstream.write(abyte0);
			}
			if(servletoutputstream != null){
				servletoutputstream.flush();
				servletoutputstream.close();
			}
		}
	}
	
	private boolean excluded(String s) {
		if(s == null || excluded == null){
			return false;
		}else{
			Matcher matcher=pExcluded.matcher(s);
			return matcher.matches();
		}
	}
	
	private boolean isToken(byte abyte0[], int i, String s) {
		String s1=s.toUpperCase();
		if(i + s1.length() >= abyte0.length){ return false; }
		String s2=new String(abyte0,i + 1,s1.length());
		return s1.equals(s2.toUpperCase());
	}
}
