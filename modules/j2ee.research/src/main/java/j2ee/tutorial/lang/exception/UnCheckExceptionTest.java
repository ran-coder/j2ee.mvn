package j2ee.tutorial.lang.exception;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-6-20 下午02:45:56  
 *   
 */
public class UnCheckExceptionTest {
	public static int test1(int i){
		if(i<1){
			throw new RuntimeException("i<1");
		}
		return i;
	}
	public static void test2(int i){
		System.out.println("test2:"+i);
	}
	public static void main(String[] args) {
		int i=1;
		test1(i);
		test2(i);
	}
}
