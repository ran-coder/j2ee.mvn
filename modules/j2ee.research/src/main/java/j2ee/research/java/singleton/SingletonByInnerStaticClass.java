package j2ee.research.java.singleton;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-16 17:50
 * To change this template use File | Settings | File Templates.
 */
public class SingletonByInnerStaticClass{
	private SingletonByInnerStaticClass(){
		System.out.println("new SingletonByInnerStaticClass");
	}
	public static SingletonByInnerStaticClass getInstance(){
		return SingletonHolder.instance;
	}
	public static void createString(){
		System.out.println("SingletonByInnerStaticClass.createString");
	}
	private static class SingletonHolder{
		private static SingletonByInnerStaticClass instance=new SingletonByInnerStaticClass();
		private SingletonHolder(){
			System.out.println("new SingletonHolder");
		}
	}
}
