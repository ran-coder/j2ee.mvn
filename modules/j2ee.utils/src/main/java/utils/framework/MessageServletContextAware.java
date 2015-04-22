package utils.framework;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author yuanwei
 * @version ctreateTime:2012-5-11 下午3:48:06
 */
public class MessageServletContextAware implements ServletContextAware {
	private ServletContext	servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
	}

	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale){
		WebApplicationContext applicationContext=WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return applicationContext.getMessage(code,args,defaultMessage,locale);
	}
}
