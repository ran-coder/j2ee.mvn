package utils.framework;
/**  
 * @author yuanwei  
 *   BeanFacotry是spring中比较原始的Factory。
 *   如XMLBeanFactory就是一种典型的BeanFactory。
 *   原始的BeanFactory无法支持spring的许多插件，如AOP功能、Web应用等。
 *   ApplicationContext接口,它由BeanFactory接口派生而来，
 *   因而提供BeanFactory所有的功能。ApplicationContext
 *   以一种更向面向框架的方式工作以及对上下文进行分层和实现继承，
 *   ApplicationContext包还提供了以下的功能：
 *   • MessageSource, 提供国际化的消息访问
 *   • 资源访问，如URL和文件
 *   • 事件传播
 *   • 载入多个（有继承关系）上下文 ，使得每一个上下文都专注于一个特定的层次，比如应用的web层
 *   http://xtu-xiaoxin.iteye.com/blog/619675
 */

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class SpringBeanUtil {
	private static ClassPathXmlApplicationContext context=null;
	static{
		try{
			//"classpath*:META-INF/applicationContext-*.xml","/WEB-INF/applicationContext-validation.xml","/WEB-INF/action-servlet.xml"
			context = new ClassPathXmlApplicationContext(new String[]{
					"classpath*:spring/v2/config/*.xml"
			});
			//System.out.println(beanFactory.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	/** default file:WebRoot/WEB-INF/applicationContext.xml */
	public static Object getBean(String beanName){
		if (beanName == null)return null;
		try{
			Object obj = context.getBean(beanName);
			if (obj == null){
				System.err.println("Lookup Object[" + beanName + "] Error!");
			}
			return obj;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	public static Object getBean(String beanName,Class<?> clazz){
		if (beanName == null)return null;
		try{
			Object obj = context.getBean(beanName,clazz.getClass());
			if (obj == null){
				System.err.println("Lookup Object[" + beanName + "] Error!");
			}
			return obj;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public static XmlBeanFactory getFactory(String path) {
		XmlBeanFactory factory=new XmlBeanFactory(new FileSystemResource(path));
		//PropertyPlaceholderConfigurer config=(PropertyPlaceholderConfigurer)factory.getBean("config");
		//config.postProcessBeanFactory(factory);
		return factory;
	}
	public static XmlBeanFactory getFactory() {
		return getFactory("./WebRoot/WEB-INF/applicationContext.xml");
	}
	
	public static XmlBeanFactory getFactoryByName(String name) {
		return getFactory("./WebRoot/WEB-INF/"+name+".xml");
	}
	
	public static AbstractApplicationContext getContext(String name){
		return new ClassPathXmlApplicationContext(new String []{""+name+".xml"});
	}
	
	/**
	 * default :file:WebRoot/WEB-INF/applicationContext.xml
	 */
	public static ClassPathXmlApplicationContext getCtx(String path){
		if(path==null || path.trim().length()<1)path="file:WebRoot/WEB-INF/applicationContext.xml";
		return new ClassPathXmlApplicationContext(new String[]{path});
	}
	/**
	 * default :file:WebRoot/WEB-INF/applicationContext.xml ֧
	 */
	public static ClassPathXmlApplicationContext getCtx(String[] path){
		if(path==null || path.length<1)path=new String[]{"file:WebRoot/WEB-INF/applicationContext.xml"};
		return new ClassPathXmlApplicationContext(path);
	}

	public static <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}
	public static <T> T getBean(ClassPathXmlApplicationContext ctx,Class<T> requiredType) {
		return ctx.getBean(requiredType);
	}
	public static <T> T getBean(ClassPathXmlApplicationContext ctx,String bean,Class<T> requiredType) {
		return ctx.getBean(bean,requiredType);
	}
	public static void addConfig(BeanDefinitionRegistry reg,String path){
		if(reg==null){
			reg = new DefaultListableBeanFactory();
		}
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(reg);
		reader.loadBeanDefinitions(new ClassPathResource(path));
		//BeanFactory bf = (BeanFactory)reg;
	}
}


