package j2ee.tutorial.interview;
/**  
 * @author yuanwei  
 * @version ctreateTime:2012-2-16 下午5:21:48
 *   
 */
public class IntExample {

	public static void main(String[] args) {
		int j = 0;
		for(int i=0; i<100; i++) {
			j = j++;
		}
		System.out.println(j);
	}

}
