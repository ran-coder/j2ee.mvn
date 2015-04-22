package utils.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import utils.StringUtil;

public class ConfigUtil {
	private ResourceBundle	resource	=null;
	private Properties		properties	=null;
	private String			xpath		="";

	/**
	 * 获得ResourceBundle,new ConfigUtil("com.base.util.config.test")
	 * @param file String 不需要带文件名的".properties"文件
	 */
	public ConfigUtil(String file) {
		resource=getResourceBundle(file);
	}

	public ConfigUtil(String file, String defaultFile) {
		resource=getResourceBundle(file);
		if(resource == null) resource=getResourceBundle(defaultFile);
	}

	/**
	 * 获得Properties,mode=1:相对class的路径,否则绝对路径
	 * @param file String,带扩展名
	 * @param mode int,"com/base/util/config/test.properties":"C:\...."
	 */
	public ConfigUtil(String file, int mode) {
		properties=getProperty(file,mode);
	}

	public ResourceBundle getResourceBundle(String path) {
		try{
			xpath=path;
			resource=ResourceBundle.getBundle(path);
		}catch(Exception e){
			resource=null;
			System.out.println("Can not get file：" + path + ".properties");
		}

		return resource;
	}

	public static Properties getProperty(String path) {
		Properties properties=new Properties();
		URL url=null;
		try{
			if((url=Thread.currentThread().getContextClassLoader().getResource(path)) == null || url == null){
				// log.warn("Not found configuration properties. " + s);
				System.out.println("Not found configuration properties：" + path);
			}else{
				properties.load(url.openStream());
				return properties;
			}
		}catch(Exception exception){

		}
		//properties.getProperty("key","defaultValue");
		return null;
	}

	public static Properties getProperty(File file) {
		Properties properties=new Properties();
			if(file == null || !file.exists() || !file.canRead()){
				//log.warn("Not found configuration properties. " + file.getAbsolutePath());
				System.out.println("Not found configuration properties：");
			}else{
				FileInputStream stream=null;
				try{
					stream=new FileInputStream(file);
					properties.load(stream);
					return properties;
				}catch(FileNotFoundException e){
					e.printStackTrace();
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					if(stream!=null){
						try{
							stream.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}
			}

		//properties.getProperty("key","defaultValue");
		return null;
	}

	public Properties getProperty(String path, int mode) {
		if(StringUtil.isEmpty(path)) return null;
		Properties properties=null;
		if(mode == 1){
			properties=new Properties();
			URL url=null;
			try{
				if((url=Thread.currentThread().getContextClassLoader().getResource(path)) == null || url == null){
					System.out.println("getProperty:Not found properties:" + path);
				}else{
					properties.load(url.openStream());
					//return properties;
				}
			}catch(Exception e){

			}
		}else{
			File file=new File(path);
			if(file == null || !file.exists() || !file.isFile() || !file.canRead()) return null;
			properties=new Properties();
			FileInputStream stream=null;
			try{
				stream=new FileInputStream(file);
				properties.load(stream);
				return properties;
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(stream!=null){
					try{
						stream.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}
		return properties;
	}

	public String get(String itemIndex) {
		return get(itemIndex,"");
	}

	public String get(String itemIndex, String defaultValue) {
		if(resource != null){
			try{
				return resource.getString(itemIndex);
			}catch(Exception e){
				System.out.println("Get Properties failure：" + xpath + "-->" + itemIndex);
				return defaultValue;
			}

		}else{
			return defaultValue;
		}
	}

	public int getInt(String itemIndex) {
		return getInt(itemIndex,-1);
	}

	public int getInt(String itemIndex, int defaultValue) {
		if(resource != null){
			try{
				return StringUtil.toInt(resource.getString(itemIndex),-1);
			}catch(Exception e){
				System.out.println("Get Properties failure：" + xpath + "-->" + itemIndex);
			}
		}
		return defaultValue;
	}

	public boolean getBoolean(String itemIndex) {
		if(resource != null){
			try{
				return StringUtil.compare(resource.getString(itemIndex),"true");
			}catch(Exception e){
				System.out.println("Get Properties failure：" + xpath + "-->" + itemIndex);
			}
		}
		return false;
	}

	public String getPropertyString(String key, String defaultValue) {
		if(properties != null){
			return properties.getProperty(key,defaultValue);
		}else{
			return defaultValue;
		}
	}

	public int getPropertyInt(String key, int defaultValue) {
		if(properties != null){
			return StringUtil.toInt(properties.getProperty(key,"-1"),defaultValue);
		}else{
			return defaultValue;
		}
	}

	public static void main(String[] args) {
		ConfigUtil cfg1=new ConfigUtil("test.properties",1);
		ConfigUtil cfg2=new ConfigUtil("c:/config.properties",2);
		System.out.println(cfg1.getPropertyString("webutil.doencode","111"));
		System.out.println(cfg2.getPropertyString("client.basepath","111"));
		//System.out.println(cfg.getBoolean("webutil.doencode"));
	}
}
