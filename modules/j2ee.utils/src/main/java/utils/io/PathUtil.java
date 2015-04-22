package utils.io;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import utils.StringUtil;

public class PathUtil {
	/** 得到类加载器 */
	public static ClassLoader getClassLoader() {
		return PathUtil.class.getClassLoader();
	}
	
	/** 得到类加载器 */
	public static ClassLoader getClassLoader(Class<?> clazz) {
		return clazz.getClassLoader();
	}
	
	public static URL getClassURL(Class<?> clazz) {
		return clazz.getResource("");
	}
	
	public static String getClassPath(Class<?> clazz) {
		return getClassURL(clazz).toString();
	}
	
	/**
	 * 获取一个类的class文件所在的绝对路径。 这个类可以是JDK自身的类，也可以是用户自定义的类，或者是第三方开发包里的类。 只要是在本程序中可以被加载的类，都可以定位到它的class文件的绝对路径。
	 * @param cls 一个对象的Class属性
	 * @return 这个类的class文件位置的绝对路径。 如果没有这个类的定义，则返回null。
	 */
	public static String getPathFromClass(Class<?> cls) throws IOException {
		String path=null;
		if(cls == null){ throw new NullPointerException(); }
		URL url=getClassLocationURL(cls);
		if(url != null){
			path=url.getPath();
			if("jar".equalsIgnoreCase(url.getProtocol())){
				try{
					path=new URL(path).getPath();
				}catch(MalformedURLException e){
				}
				int location=path.indexOf("!/");
				if(location != -1){
					path=path.substring(0,location);
				}
			}
			File file=new File(path);
			path=file.getCanonicalPath();
		}
		return path;
	}
	
	/**
	 * 这个方法可以通过与某个类的class文件的相对路径来获取文件或目录的绝对路径。 通常在程序中很难定位某个相对路径，特别是在B/S应用中。 通过这个方法，我们可以根据我们程序自身的类文件的位置来定位某个相对路径。 比如：某个txt文件相对于程序的Test类文件的路径是../../resource/test.txt，
	 * 那么使用本方法Path.getFullPathRelateClass("../../resource/test.txt",Test.class) 得到的结果是txt文件的在系统中的绝对路径。
	 * @param relatedPath 相对路径
	 * @param cls 用来定位的类
	 * @return 相对路径所对应的绝对路径
	 * @throws IOException 因为本方法将查询文件系统，所以可能抛出IO异常
	 */
	public static String getFullPathRelateClass(String relatedPath, Class<?> cls) throws IOException {
		String path=null;
		if(relatedPath == null){ throw new NullPointerException(); }
		String clsPath=getPathFromClass(cls);
		File clsFile=new File(clsPath);
		String tempPath=clsFile.getParent() + File.separator + relatedPath;
		File file=new File(tempPath);
		path=file.getCanonicalPath();
		return path;
	}
	
	/**
	 * 获取类的class文件位置的URL。这个方法是本类最基础的方法，供其它方法调用。
	 */
	private static URL getClassLocationURL(final Class<?> cls) {
		if(cls == null) throw new IllegalArgumentException("null input: cls");
		URL result=null;
		final String clsAsResource=cls.getName().replace('.','/').concat(".class");
		final ProtectionDomain pd=cls.getProtectionDomain();
		// java.lang.Class contract does not specify
		// if 'pd' can ever be null;
		// it is not the case for Sun's implementations,
		// but guard against null
		// just in case:
		if(pd != null){
			final CodeSource cs=pd.getCodeSource();
			// 'cs' can be null depending on
			// the classloader behavior:
			if(cs != null) result=cs.getLocation();
			
			if(result != null && "file".equals(result.getProtocol())){
				// Convert a code source location into
				// a full class file location
				// for some common cases:
				try{
					if(result.toExternalForm().endsWith(".jar") || result.toExternalForm().endsWith(".zip")) result=new URL("jar:".concat(result.toExternalForm()).concat("!/")
							.concat(clsAsResource));
					else if(new File(result.getFile()).isDirectory()) result=new URL(result,clsAsResource);
				}catch(MalformedURLException ignore){
				}
			}
		}
		
		if(result == null){
			// Try to find 'cls' definition as a resource;
			// this is not
			// document．d to be legal, but Sun's
			// implementations seem to //allow this:
			final ClassLoader clsLoader=cls.getClassLoader();
			result=clsLoader != null ? clsLoader.getResource(clsAsResource) : ClassLoader.getSystemResource(clsAsResource);
		}
		return result;
	}
	
	public static String toUrl(String path) {
		// Is our work already done for us?
		if(path.startsWith("file:/")){ return path; }
		
		return toUrl(new File(path));
	}
	
	@SuppressWarnings("deprecation")
	public static String toUrl(File file) {
		try{
			return file.toURL().toExternalForm();
		}catch(MalformedURLException e){
			String pathCorrected=StringUtil.replaceChars(file.getAbsolutePath(),'\\','/');
			if(pathCorrected.startsWith("file:/")){ return pathCorrected; }
			
			return "file://" + pathCorrected;
		}
	}
	
	/**
	 * Given a basedir and a child file, return the relative path to the child.
	 * @param basedir the basedir.
	 * @param file the file to get the relative path for.
	 * @return the relative path to the child. (NOTE: this path will NOT start with a {@link File#separator} character)
	 */
	public static String getRelative(String basedir, File file) {
		return getRelative(basedir,file.getAbsolutePath());
	}
	
	/**
	 * Given a basedir and a child file, return the relative path to the child.
	 * @param basedir the basedir.
	 * @param child the child path (can be a full path)
	 * @return the relative path to the child. (NOTE: this path will NOT start with a {@link File#separator} character)
	 */
	public static String getRelative(String basedir, String child) {
		if(basedir.endsWith("/") || basedir.endsWith("\\")){
			basedir=basedir.substring(0,basedir.length() - 1);
		}
		
		if(child.startsWith(basedir)){
			// simple solution.
			return child.substring(basedir.length() + 1);
		}
		
		String absoluteBasedir=new File(basedir).getAbsolutePath();
		if(child.startsWith(absoluteBasedir)){
			// resolved basedir solution.
			return child.substring(absoluteBasedir.length() + 1);
		}
		
		// File is not within basedir.
		throw new IllegalStateException("Unable to obtain relative path of file " + child + ", it is not within basedir " + basedir + ".");
	}
	public static String convertPath4Unix(String path) {
		if (path == null) {
			return null;
		}
		String tempPath = path;
		tempPath = tempPath.replaceAll("\r\n", "\n");
		tempPath = tempPath.replaceAll("\\\\", "/");
		return tempPath;
	}
	public static String getClassesPath() {
		String path = PathUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return path;   
	}
	
	public static String getWebInfPath() {
		String path = getClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") + 8);
		} else {
			return null;
		}
		return path;
	}
	
	public static String getWebRoot() {
		String path = getClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/classes"));   
		} else {
			return null;
		}
		return path;
	}
	public static void debugweb(){
        System.out.println("getWebClassesPath = " + getClassesPath());   
        System.out.println("getWebInfPath = " + getWebInfPath());   
        System.out.println("getWebRoot = " + getWebRoot()); 
	}
	public static void debug() throws IOException {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("") + "\tThread.currentThread().getContextClassLoader().getResource('')");
		System.out.println(PathUtil.class.getClassLoader().getResource("") + "\tPathUtil.class.getClassLoader().getResource(\"\")");
		System.out.println(ClassLoader.getSystemResource("") + "\tClassLoader.getSystemResource(\"\")");
		System.out.println(com.google.common.io.Files.class.getResource("") + "\tFiles.class.getResource('')");
		System.out.println(PathUtil.class.getResource("") + "\tPathUtil.class.getResource('')");
		System.out.println(PathUtil.class.getResource(".") + "\tPathUtil.class.getResource('.')");
		System.out.println(PathUtil.class.getResource("./") + "\tPathUtil.class.getResource('./')");
		System.out.println(PathUtil.class.getResource("/") + "\tPathUtil.class.getResource('/')"); // Class文件所在路径
		// System.out.println(new File("/").getAbsolutePath()+"\tnew File('/').getAbsolutePath()");
		System.out.println(System.getProperty("user.dir") + "\tSystem.getProperty('user.dir')");
		
		System.out.println(getPathFromClass(PathUtil.class) + "\tgetPathFromClass(PathUtil.class)");
		System.out.println(getFullPathRelateClass("../../webutil.properties",PathUtil.class) + "\tgetFullPathRelateClass('../../webutil.properties')");
	}
	
	public static void main(String[] args) throws IOException {
		//debug();
		debugweb();
	}
}
