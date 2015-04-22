package j2ee.tutorial.pattrens.observer.sun;

/**
 * @author yuanwei
 * @version ctreateTime:2011-5-6 下午02:00:57
 */

import java.util.Observable;

public class Watched extends Observable {
	private String	data	="";

	public String retrieveData() {
		return data;
	}

	public void changeData(String data) {
		if(!this.data.equals(data)){
			this.data=data;
			setChanged();
		}

		notifyObservers();
	}
}
