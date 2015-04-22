package j2ee.research.java.singleton;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-16 17:40
 * To change this template use File | Settings | File Templates.
 */
public class Singleton{
	private static Singleton instance=new Singleton();
	private Singleton(){
		System.out.println("new Singleton");
	}
	public static Singleton getInstance(){
		return instance;
	}
	public static void createString(){
		System.out.println("Singleton.createString");
	}
}
