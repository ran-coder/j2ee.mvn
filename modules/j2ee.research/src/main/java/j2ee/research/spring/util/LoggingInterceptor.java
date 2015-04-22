package j2ee.research.spring.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author admin
 * preHandler()会在Controller处理请求之前被呼叫，
 * 传回的boolean快定是否呼叫接下来的Handler Interceptor或是Controller来处理请求，如果传回false，则接下来的Interceptor或Controller就不处理请求，
 * postHandler()则会在Controller处理完请求之后被呼叫，
 * afterCompletion()方法会在View绘制完成之后被呼叫。
 */
public class LoggingInterceptor extends HandlerInterceptorAdapter {
	protected static Logger log=LoggerFactory.getLogger(LoggingInterceptor.class);
	//private static Log logger=LogFactory.getLog(LoggingInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info(handler.getClass().getName() + " 开始执行...");
		return true;
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info(handler.getClass().getName() + " 执行完毕...");
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info("请求处理完毕...");
	}
}