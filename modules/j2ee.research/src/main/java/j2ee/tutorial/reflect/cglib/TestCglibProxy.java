package j2ee.tutorial.reflect.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by IntelliJ IDEA.<br>
 * <b>User</b>: leizhimin<br>
 * <b>Date</b>: 2008-5-14 0:41:01<br>
 * <b>Note</b>: Please add comment here!
 */
public class TestCglibProxy {

	public static void main(String rags[]) {
		//Target target=new Target();
		TestCglibProxy test=new TestCglibProxy();
		Target proxyTarget=(Target)test.createProxy(Target.class);
		String res=proxyTarget.execute();
		System.out.println(res);
	}

	public Object createProxy(Class<?> targetClass) {
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(new MyMethodInterceptor());
		return enhancer.create();
	}
}