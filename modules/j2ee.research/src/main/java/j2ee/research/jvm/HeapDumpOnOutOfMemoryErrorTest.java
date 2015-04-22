package j2ee.research.jvm;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2014-04-16 15:43
 * To change this template use File | Settings | File Templates.
 */
public class HeapDumpOnOutOfMemoryErrorTest{
	public static void main(String[] args) {
		long arr[]={};
		for (int i=1; i<=10000000; i*=2) {
			arr = new long[i];
		}
		System.out.println("size : " + arr.length);
		Runtime runtime = Runtime.getRuntime();
		System.out.printf("maxMemory : %.2fM\n", runtime.maxMemory()*1.0/1024/1024);
		System.out.printf("totalMemory : %.2fM\n", runtime.totalMemory()*1.0/1024/1024);
		System.out.printf("freeMemory : %.2fM\n", runtime.freeMemory()*1.0/1024/1024);
	}
}
