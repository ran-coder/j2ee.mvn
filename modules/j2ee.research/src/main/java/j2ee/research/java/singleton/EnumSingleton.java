package j2ee.research.java.singleton;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-16 17:40
 * To change this template use File | Settings | File Templates.
 */
public enum EnumSingleton{
	v1;
	private EnumSingleton(){
		System.out.println("new EnumSingleton");
	}
	public static void createString(){
		System.out.println("EnumSingleton.createString");
	}
}
