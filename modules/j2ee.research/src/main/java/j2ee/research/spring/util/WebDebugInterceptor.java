package j2ee.research.spring.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 
 * 默认不会执行,debug设置为true才执行
 * preHandler()会在Controller处理请求之前被呼叫，
 * 传回的boolean快定是否呼叫接下来的Handler Interceptor或是Controller来处理请求，如果传回false，则接下来的Interceptor或Controller就不处理请求，
 * postHandler()则会在Controller处理完请求之后被呼叫，
 * afterCompletion()方法会在View绘制完成之后被呼叫。<br/>
 * 
 * 	<bean id="urlMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
 *		<property name="interceptors">
 *		<list>
 *				<bean class="com.fltrp.util.interceptor.WebDebugInterceptor">
 *					<property name="debug" value="true"/>
 *				</bean>
 *			</list>
 *		</property>
 */
public class WebDebugInterceptor extends HandlerInterceptorAdapter {
	private static Logger log=LoggerFactory.getLogger(WebDebugInterceptor.class);
	private long preHandleTime;
	private long postHandleTime;
	private boolean debug;
	private long limit;
	public void setDebug(boolean debug) {
		this.debug=debug;
	}
	public void setLimit(long limit) {
		this.limit=limit;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		preHandleTime=System.currentTimeMillis();
		return debug;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		postHandleTime=System.currentTimeMillis()-preHandleTime;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long afterCompletionTime=System.currentTimeMillis()-postHandleTime-preHandleTime;//视图绘制时间
		if(limit>0 && (afterCompletionTime+postHandleTime)<limit)return;
		if(debug && log.isInfoEnabled()){
			//String queryString=request.getQueryString();
			log.info(new StringBuilder(1024)
			.append('[').append(postHandleTime).append('+')//controller执行时间
			.append(afterCompletionTime)//视图绘制时间
			.append(']').append('m').append('s').append(',')
			.append(RequestUtil.getShortRequestURL(request)).append(',')
			.append(handler==null?"":handler.getClass().getSimpleName()).append(',')
			.append(RequestUtil.getIpAddr(request))
			//.append(',').append(UserUtil.getCurrentUserName())
			//.append(RequestUtil.getRequestURL(request))
			.toString());
		}
		if(debug){
			request.setAttribute("postHandleTime",postHandleTime);
			request.setAttribute("afterCompletionTime",afterCompletionTime);
			response.addHeader("debug_controllerTime",String.valueOf(postHandleTime));
			response.addHeader("debug_createViewTime",String.valueOf(afterCompletionTime));
		}
	}
	

}