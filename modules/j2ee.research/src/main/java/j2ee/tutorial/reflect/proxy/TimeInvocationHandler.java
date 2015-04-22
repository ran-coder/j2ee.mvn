package j2ee.tutorial.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-11 上午11:15:44  
 *   
 */
public class TimeInvocationHandler implements InvocationHandler {
	private Object target;
	public TimeInvocationHandler(Object target){
		this.target=target;
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("proxy:"+proxy.getClass());
		long start=System.nanoTime();
		Object result=method.invoke(target,args);
		System.out.println("time:"+(System.nanoTime()-start));
		return result;
	}

}
