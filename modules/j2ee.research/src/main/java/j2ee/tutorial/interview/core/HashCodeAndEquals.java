package j2ee.tutorial.interview.core;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-11-15 10:24
 * To change this template use File | Settings | File Templates.
 */
public class HashCodeAndEquals{
	public static void main(String[] args) {
		//equals相同,则hashcode相同
		//hashcode相同,equals不一定相同
		//hashmap,先比较hashcode,再比较equals,存入hash的节点
		int hash1 = "ABCDEa123abc".hashCode();
		int hash2 = "ABCDFB123abc".hashCode();
		System.out.println("hash1==hash2:"+hash1+"=="+hash2+":"+(hash1==hash2));

	}
}
