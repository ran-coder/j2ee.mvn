package j2ee.tutorial.pattrens.observer.sxt.v2;

import java.util.ArrayList;
import java.util.List;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-14 上午10:45:00
 *   
 */
public class Baby implements Runnable {
	private List<WeakUpListener> listeners;
	public void addWeakUpListener(WeakUpListener listener){
		if(listeners==null){
			this.listeners=new ArrayList<WeakUpListener>();
		}
		listeners.add(listener);
	}
	public void run() {
		try{
			Thread.sleep(3000);
		}catch(InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(listeners==null)return;
		for(WeakUpListener listener:listeners){
			listener.doWeakUpEvent();
		}
	}

}
