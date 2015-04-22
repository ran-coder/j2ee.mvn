package tutorial.pattrens.observer.sxt.v2;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-14 上午10:50:10
 *   
 */
public class Dad implements WeakUpListener {
	public void doWeakUpEvent() {
		System.out.println("Dad feed baby.");
	}

}
