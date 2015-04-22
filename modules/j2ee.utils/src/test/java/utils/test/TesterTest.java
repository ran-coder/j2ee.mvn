package utils.test;

import org.junit.Test;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-10-25 上午9:41:21
 *   
 */
public class TesterTest {

	@Test
	public final void testRun() {
		new Tester("TesterTest.testRun") {
			@Override
			public void test() throws Exception {
				System.out.println("test");
			}
		}.run();
	}

}
