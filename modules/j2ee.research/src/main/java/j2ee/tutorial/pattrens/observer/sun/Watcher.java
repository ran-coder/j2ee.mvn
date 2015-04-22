package j2ee.tutorial.pattrens.observer.sun;

/**
 * @author yuanwei
 * @version ctreateTime:2011-5-6 下午02:01:24
 */

import java.util.Observable;
import java.util.Observer;

public class Watcher implements Observer {
	public Watcher(Watched watched) {
		watched.addObserver(this);
	}

	public void update(Observable ob, Object arg) {
		System.out.println("Data has been changed to: '" + ((Watched)ob).retrieveData() + "'");
	}
}
