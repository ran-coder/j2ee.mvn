package utils.framework.freemarker;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import utils.framework.Freemarkers;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * @author yuanwei
 * @version ctreateTime:2012-7-16 下午5:04:24
 */
public class FreemarkersTest {
	// final static String baseDir="D:/Server/IDE/eclipseWork/j2ee.mvn/modules/j2ee.utils/src/test/resources/freemarker/";
	Configuration	config	=null;
	
	@Before
	public final void initConfig() {
		try{
			// config=Freemarkers.initConfig("D:/Server/IDE/eclipseWork/j2ee.mvn/modules/j2ee.utils/src/test/resources/freemarker",null);
			// config=Freemarkers.initConfig(baseDir,null);
			config=Freemarkers.initConfig(FreemarkersTest.class,"/utils/framework/freemarker/",null);
			// config.setSetting("template_exception_handler","ignore");
		}catch(IOException e){
			e.printStackTrace();
		}
		assertNotNull(config);
	}
	
	//@Test
	public final void testCreate() {
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("username","Big Joe");
		data.put("latestProductUrl","http://hellobooky.com");
		data.put("latestProductName","latestProductName");
		try{
			Freemarkers.create(config,"hello.ftl","UTF-8",data,System.out);
		}catch(IOException e){
			e.printStackTrace();
		}catch(TemplateException e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public final void testMacro() {
		List<User> userList=Arrays.asList(new User(11,36,"张起灵"),new User(22,32,"吴三省"),new User(33,29,"陈文锦"));
		List<Student> stuList=Arrays.asList(new Student("stu11","张小凡"),new Student("stu22","林惊羽"),new Student("stu33","曾书书"));
		Map<String, Object> rootMap=new HashMap<String, Object>();
		rootMap.put("userList",userList);
		rootMap.put("stuList",stuList);
		try{
			Freemarkers.create(config,"selectUsage.ftl","UTF-8",rootMap,System.out);
		}catch(IOException e){
			e.printStackTrace();
		}catch(TemplateException e){
			e.printStackTrace();
		}
	}
	@Test
	public final void testPage() {
		Map<String, Object> rootMap=new HashMap<String, Object>();
		rootMap.put("ipage",1);
		rootMap.put("itotal",88);
		try{
			Freemarkers.create(config,"pageUsage.ftl","UTF-8",rootMap,System.out);
		}catch(IOException e){
			e.printStackTrace();
		}catch(TemplateException e){
			e.printStackTrace();
		}
	}
}
