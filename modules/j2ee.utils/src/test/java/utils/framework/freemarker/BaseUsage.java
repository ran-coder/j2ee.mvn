package utils.framework.freemarker;

import java.io.IOException;
import java.util.Map;

import utils.framework.Freemarkers;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-8-10 下午4:43:45
 *   
 */
public class BaseUsage {
	static Configuration	config	=null;
	static{initConfig();}
	public static void initConfig() {
		try{
			// config=Freemarkers.initConfig("D:/Server/IDE/eclipseWork/j2ee.mvn/modules/j2ee.utils/src/test/resources/freemarker",null);
			// config=Freemarkers.initConfig(baseDir,null);
			config=Freemarkers.initConfig(FreemarkersTest.class,"/utils/framework/freemarker/",null);
			// config.setSetting("template_exception_handler","ignore");
			config.setClassicCompatible(true);//不显示null
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void show(String name,Map<String,Object> data){
		try{
			Freemarkers.create(config,name,"UTF-8",data,System.out);
		}catch(IOException e){
			e.printStackTrace();
		}catch(TemplateException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		show("base.ftl",null);
	}
	
}
