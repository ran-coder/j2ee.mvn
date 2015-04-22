package utils.framework;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-5-11 下午3:37:48
 *   
 */
public class SpringWebAppUtil{
	public static WebApplicationContext getWebContext(HttpServletRequest request){
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		return applicationContext;
	}
	public static Locale getLocale(HttpServletRequest request){
		return RequestContextUtils.getLocale(request);
	}
	/** 国际化:http://blog.csdn.net/geloin/article/details/7549913 */
	public static String getMessage(HttpServletRequest request,String key){
		//"text.menu.name"
		return getWebContext(request).getMessage(key,null, getLocale(request));
	}
}
