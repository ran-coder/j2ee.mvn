package j2ee.tutorial.pattrens.observer.sxt.v1;


/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-14 上午10:31:11
 *   
 */
public class Child implements Runnable {
	private Dad dad;
	public Child(Dad dad){
		this.dad=dad;
	}
	public void run() {
		try{
			Thread.sleep(3000);
		}catch(InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Baby is weakup.");
		dad.feed(this);
	}


}
