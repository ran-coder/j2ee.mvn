package j2ee.tutorial.apache.util.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import utils.io.PathUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerTest {
	
	public static void main(String[] args) throws IOException, TemplateException {
		StringWriter writer=new StringWriter();
		// Create a Configuration object for FreeMarker
		Configuration cfg=new Configuration();
		cfg.setTemplateLoader(new ClassTemplateLoader(FreemarkerTest.class,"."));
		cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		// The root Map serves as a Context for our template engine
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("enrollment","");
		// A template is processed with a Map and output is sent to a Writer.
		Template template=cfg.getTemplate(PathUtil.getFullPathRelateClass("template.ftl",FreemarkerTest.class));
		template.process(root,writer);
		System.out.println("output: \n" + writer.toString());

	}
}
