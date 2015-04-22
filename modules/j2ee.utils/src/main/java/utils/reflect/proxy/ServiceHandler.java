package utils.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ServiceHandler implements InvocationHandler,BaseHandler {
	private Object	proxy;

	public Object bind(Object obj) {
		this.proxy = obj;
		return Proxy.newProxyInstance(
				obj.getClass().getClassLoader(),obj.getClass().getInterfaces(), this);
	}

	public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
		Object result=null;
		System.out.println("方法调用前...");
		result=method.invoke(this.proxy,args);
		System.out.println("方法调用后...");
		return result;
	}

}
