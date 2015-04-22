package utils.framework.velocity;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.framework.Velocitys;

/**
 * @author yuanwei
 * @version ctreateTime:2012-7-25 下午3:59:42
 */
public class VelocitysTest {
	final static String	baseDir	="D:/Server/IDE/eclipseWork/j2ee.mvn/modules/j2ee.utils/src/test/resources/";
	VelocityEngine		engine;
	
	@Before
	public void setUp() throws Exception {
		// resource.loader=class
		// class.resource.loader.class=org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
		// velocimacro.library
		// file.resource.loader.path = /vm
		Map<String, Object> config=new HashMap<String, Object>();
		config.put(RuntimeConstants.INPUT_ENCODING,"UTF-8");
		config.put(RuntimeConstants.OUTPUT_ENCODING,"UTF-8");
		config.put(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE,true);

		config.put(RuntimeConstants.RESOURCE_LOADER,"classpath,webapp,class");
		config.put("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader ");
		//config.put("classpath.resource.loader.class",ClasspathResourceLoader.class.getName());config.put("resourceLoaderPath", "/WEB-INF/classes/templates");

		///config.put(RuntimeConstants.RESOURCE_LOADER,"file");
		//config.put("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		//config.put(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,baseDir);

		//config.put( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.Log4JLogChute");
		//config.put("runtime.log.logsystem.log4j.logger",LOGGER_NAME);
		engine=Velocitys.initEngine(config);
	}
	
	@After
	public void tearDown() throws Exception {}
	
	//@Test
	public final void testRead1() {
		StringWriter writer=new StringWriter(2048);
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("username","Big Joe");
		data.put("latestProductUrl","http://hellobooky.com");
		data.put("latestProductName","latestProductName");
		Velocitys.read(engine,"utils/framework/velocity/index.vm","UTF-8",data,writer);
		System.out.println(writer.toString());
	}
	@Test
	public final void testRead() {
		StringWriter writer=new StringWriter(2048);
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("username","Big Joe");
		data.put("latestProductUrl","http://hellobooky.com");
		data.put("latestProductName","中文");
		Velocitys.read(engine,"utils/framework/velocity/var.vm","UTF-8",data,writer);
		System.out.println(writer.toString());
	}
	@Test
	public final void testMacro() {
		StringWriter writer=new StringWriter(2048);
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("username","Big Joe");
		data.put("latestProductUrl","http://hellobooky.com");
		data.put("latestProductName","中文");
		Velocitys.read(engine,"utils/framework/velocity/pageUsage.vm","UTF-8",data,writer);
		System.out.println(writer.toString());
	}
}
