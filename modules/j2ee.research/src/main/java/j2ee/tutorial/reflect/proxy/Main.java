package j2ee.tutorial.reflect.proxy;

import java.lang.reflect.Proxy;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-11 上午11:02:45  
 *   
 */
public class Main {
	public static void main(String[] args) {
		System.out.println(Main.class.getClassLoader());
		System.out.println(ClassLoader.getSystemClassLoader());
		Moveable car=new Car();
		Moveable proxy=(Moveable)Proxy.newProxyInstance(Main.class.getClassLoader(),//ClassLoader.getSystemClassLoader()
				Car.class.getInterfaces(),new TimeInvocationHandler(car)) ;
		proxy.move();
		System.out.println("*********************");
	}

}