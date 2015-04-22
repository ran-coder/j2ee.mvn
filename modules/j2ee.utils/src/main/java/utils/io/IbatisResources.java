package utils.io;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   Resources.java


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Properties;

public class IbatisResources {
	private static ClassLoader	defaultClassLoader;
	private static Charset		defaultCharset;
	
	private IbatisResources() {}

	public static ClassLoader getDefaultClassLoader() {
		return defaultClassLoader;
	}

	public static void setDefaultClassLoader(ClassLoader classLoader) {
		defaultClassLoader=classLoader;
	}

	public static URL getResourceURL(String resource) throws IOException {
		return getResourceURL(getClassLoader(),resource);
	}

	public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
		URL url=null;
		if(loader != null) url=loader.getResource(resource);
		if(url == null) url=ClassLoader.getSystemResource(resource);
		if(url == null) throw new IOException((new StringBuilder()).append("Could not find resource ").append(resource).toString());
		else return url;
	}

	public static InputStream getResourceAsStream(String resource) throws IOException {
		return getResourceAsStream(getClassLoader(),resource);
	}

	public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
		InputStream in=null;
		if(loader != null) in=loader.getResourceAsStream(resource);
		if(in == null) in=ClassLoader.getSystemResourceAsStream(resource);
		if(in == null) throw new IOException((new StringBuilder()).append("Could not find resource ").append(resource).toString());
		else return in;
	}

	public static Properties getResourceAsProperties(String resource) throws IOException {
		Properties props=new Properties();
		InputStream in=null;
		String propfile=resource;
		in=getResourceAsStream(propfile);
		props.load(in);
		in.close();
		return props;
	}

	public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
		Properties props=new Properties();
		InputStream in=null;
		String propfile=resource;
		in=getResourceAsStream(loader,propfile);
		props.load(in);
		in.close();
		return props;
	}

	public static Reader getResourceAsReader(String resource) throws IOException {
		Reader reader;
		if(defaultCharset == null) reader=new InputStreamReader(getResourceAsStream(resource));
		else reader=new InputStreamReader(getResourceAsStream(resource),defaultCharset);
		return reader;
	}

	public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
		Reader reader;
		if(defaultCharset == null) reader=new InputStreamReader(getResourceAsStream(loader,resource));
		else reader=new InputStreamReader(getResourceAsStream(loader,resource),defaultCharset);
		return reader;
	}

	public static File getResourceAsFile(String resource) throws IOException {
		return new File(getResourceURL(resource).getFile());
	}

	public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
		return new File(getResourceURL(loader,resource).getFile());
	}

	public static InputStream getUrlAsStream(String urlString) throws IOException {
		URL url=new URL(urlString);
		URLConnection conn=url.openConnection();
		return conn.getInputStream();
	}

	public static Reader getUrlAsReader(String urlString) throws IOException {
		return new InputStreamReader(getUrlAsStream(urlString));
	}

	public static Properties getUrlAsProperties(String urlString) throws IOException {
		Properties props=new Properties();
		InputStream in=null;
		String propfile=urlString;
		in=getUrlAsStream(propfile);
		props.load(in);
		in.close();
		return props;
	}

	@SuppressWarnings({ "rawtypes" })
	public static Class classForName(String className) throws ClassNotFoundException {
		Class clazz=null;
		try{
			clazz=getClassLoader().loadClass(className);
		}catch(Exception e){
		}
		if(clazz == null) clazz=Class.forName(className);
		return clazz;
	}

	private static ClassLoader getClassLoader() {
		if(defaultClassLoader != null) return defaultClassLoader;
		else return Thread.currentThread().getContextClassLoader();
	}

	public static Charset getCharset() {
		return defaultCharset;
	}

	public static void setCharset(Charset charset) {
		defaultCharset=charset;
	}


}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\Server\lib\apache\ibatis-2.3.4.726\lib\ibatis-2.3.4.726.jar
	Total time: 94 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/