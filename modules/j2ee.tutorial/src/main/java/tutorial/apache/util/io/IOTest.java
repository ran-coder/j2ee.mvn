package tutorial.apache.util.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class IOTest {
	public static void setProxy() {
		System.setProperty("http.proxyType", "4");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("http.proxyHost", "172.17.18.80");
		System.setProperty("http.proxySet", "true");
	}
	public static void readUrl(String url) throws MalformedURLException, IOException{
		InputStream in = new URL(url).openStream();
		try {
			System.out.println(IOUtils.toString( in ));
		} catch (Exception e) {
		} finally{
			IOUtils.closeQuietly(in);
		}
	}
	
	public static void listFile(String path,String[] file,boolean recursive){
		//Collection c=FileUtils.listFiles(new File("C:/WINDOWS/system32"), new SuffixFileFilter(new String[]{".dll",".log"}), FalseFileFilter.INSTANCE);
		Collection<File> c=FileUtils.listFiles(new File(path),file,recursive);
		for (Object o:c.toArray()) {
			System.out.println(o);
		}
	}
	public static void listDir(String path,boolean recursive){
		//Collection c=FileUtils.listFiles(new File("C:/WINDOWS/system32"), new SuffixFileFilter(new String[]{".dll",".log"}), FalseFileFilter.INSTANCE);
		File[] files=new File(path).listFiles(new FileFilter(){
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		for (File f:files) {
			System.out.println(f.getName());
		}
	}
	
	public static void main(String[] args) throws Exception {
		//setProxy();
		//readUrl("http://commons.apache.org/io/bestpractices.html");
		//listFile("D:/", new String[]{"java","txt"}, true);
		listDir("C:/WINDOWS/system32",true);
	}

}
