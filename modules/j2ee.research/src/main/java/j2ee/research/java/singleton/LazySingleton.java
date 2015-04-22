package j2ee.research.java.singleton;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-16 17:49
 * To change this template use File | Settings | File Templates.
 */
public class LazySingleton{
	private static LazySingleton instance=null;
	private LazySingleton(){
		System.out.println("new LazySingleton");
	}
	public synchronized static LazySingleton getInstance(){
		if(instance==null)instance=new LazySingleton();
		return instance;
	}
	public static void createString(){
		System.out.println("LazySingleton.createString");
	}
}
