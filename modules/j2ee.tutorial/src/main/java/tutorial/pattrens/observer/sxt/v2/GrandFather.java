package tutorial.pattrens.observer.sxt.v2;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-14 上午10:50:51
 *   
 */
public class GrandFather implements WeakUpListener {

	public void doWeakUpEvent() {
		System.out.println("hug baby.");
	}

}
