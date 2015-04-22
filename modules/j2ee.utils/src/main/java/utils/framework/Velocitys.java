package utils.framework;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 * @author yuanwei
 * @version ctreateTime:2012-7-25 下午3:34:39
 */
public class Velocitys {
	public static void init(Map<String,Object> config) {
		//resource.loader=class
		//class.resource.loader.class=org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
		//velocimacro.library
		//file.resource.loader.path = /vm
		//file.resource.loader.cache= true
		//file.resource.loader.modificationCheckInterval=3
		//input.encoding=utf-8
		//output.encoding=utf-8
		if(config!=null){
			for(Map.Entry<String,Object> entry:config.entrySet()){
				Velocity.setProperty(entry.getKey(),entry.getValue());
			}
			Velocity.init();
		}else{
			config=new HashMap<String, Object>();
			config.put(RuntimeConstants.INPUT_ENCODING,"UTF-8");
			config.put(RuntimeConstants.OUTPUT_ENCODING,"UTF-8");
			config.put(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE,true);
			init(config);
		}
	}
	public static VelocityEngine initEngine(Map<String,Object> config) {
		if(config!=null&&!config.isEmpty()){
			VelocityEngine engine=new VelocityEngine();
			for(Map.Entry<String,Object> entry:config.entrySet()){
				engine.setProperty(entry.getKey(),entry.getValue());
			}
			engine.init();
			return engine;
		}else{
			config=new HashMap<String, Object>();
			config.put(RuntimeConstants.INPUT_ENCODING,"UTF-8");
			config.put(RuntimeConstants.OUTPUT_ENCODING,"UTF-8");
			config.put(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE,true);
			return initEngine(config);
		}
	}

	public static void read(VelocityEngine engine,String templateName,String encoding,Map<String,Object> data,Writer writer) {
		Template template=engine.getTemplate(templateName,encoding);
		VelocityContext context=new VelocityContext();
		if(data!=null&&!data.isEmpty()){
			for(Map.Entry<String,Object> entry:data.entrySet()){
				context.put(entry.getKey(),entry.getValue());
			}
		}
		template.merge(context,writer);
	}
}
