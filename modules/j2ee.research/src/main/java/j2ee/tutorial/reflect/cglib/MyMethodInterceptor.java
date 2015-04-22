package j2ee.tutorial.reflect.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.<br>
 * <b>User</b>: leizhimin<br>
 * <b>Date</b>: 2008-5-14 0:39:08<br>
 * <b>Note</b>: Please add comment here!
 */
public class MyMethodInterceptor implements MethodInterceptor {

	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.out.println(">>>MethodInterceptor start...");
		Object result=methodProxy.invokeSuper(object,args);
		System.out.println(">>>MethodInterceptor ending:"+result.toString());
		return "hahahh";
	}
}