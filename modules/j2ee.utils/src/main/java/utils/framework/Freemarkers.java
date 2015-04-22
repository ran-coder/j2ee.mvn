package utils.framework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author yuanwei
 * @version ctreateTime:2012-7-16 下午4:48:00
 */
public class Freemarkers {
	/**
	 * @param clazz
	 * @param path
	 * @param defaultEncoding UTF-8默认
	 * @return
	 * @throws IOException
	 * <br/>1.clazz==null,path为绝对路径
	 * <br/>2.clazz!=null,path为相对/的路径 例如:utils.framework.freemarker.FreemarkersTest.class,"/utils/framework/freemarker/"
	 */
	public static Configuration initConfig(Class<?> clazz,String path, String defaultEncoding) throws IOException {
		Configuration config=null;
		config=new Configuration();
		config.setDefaultEncoding(defaultEncoding == null ? "UTF-8" : defaultEncoding);
		if(clazz==null){
			if(path!=null)config.setDirectoryForTemplateLoading(new File(path));
		}else{
			config.setClassForTemplateLoading(clazz,path);
		}
		config.setObjectWrapper(new DefaultObjectWrapper());
		//忽略异常两种方法
		//1 config.setSetting("template_exception_handler","ignore");"rethrow", "debug", "html_debug", "ignore" (case insensitive). 
		//2 自定义继承TemplateExceptionHandler,cfg.setTemplateExceptionHandler(new MyTemplateExceptionHandler()); 
		return config;
	}

	/**
	 * 生成静态文件<br>
	 * create(config,"html/magazine/search.ftl", null, "/magazine/search.html");<br>
	 *  其中“html/magazine/search.ftl”是在“/WEB-INF/template”目录下。<br>
	 *  这里还需要注意的是。模版文件（search.ftl）中如果还要引用其他文件，<br>
	 *  它的路径也是不需要添加总路径“/WEB-INF/template”。<br>
	 * @param ftlTemplate ftl模版文件
	 * @param data ftl要用到的动态内容
	 * @param savePath 文件保存路径+保存文件名
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void create(Configuration config,String ftlTemplate, Map<String,Object> data, String savePath) throws IOException, TemplateException {
		Template temp=config.getTemplate(ftlTemplate);
		/* Merge data model with template */

		File file=new File(savePath);
		if(!file.exists() && !file.mkdirs()) return;

		Writer out=new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
		temp.process(data,out);
		out.flush();
	}
	public static void create(Configuration config,String ftlTemplate,String encoding, Map<String,Object> data,OutputStream out) throws IOException, TemplateException {
		if(encoding==null)encoding="UTF-8";
		Template temp=config.getTemplate(ftlTemplate,encoding);
		/* Merge data model with template */
		Writer writer=new OutputStreamWriter(out,"UTF-8");
		temp.process(data,writer);
		writer.flush();
	}
	public static void createWithoutTemplateCache(Class<?> clazz,String path,String ftlTemplate,String encoding, Map<String,Object> data,OutputStream out) throws IOException, TemplateException {
		if(encoding==null)encoding="UTF-8";
		Configuration config=initConfig(clazz,path,encoding);
		Template temp=config.getTemplate(ftlTemplate,encoding);
		/* Merge data model with template */
		Writer writer=new OutputStreamWriter(out,"UTF-8");
		temp.process(data,writer);
		writer.flush();
	}
}
