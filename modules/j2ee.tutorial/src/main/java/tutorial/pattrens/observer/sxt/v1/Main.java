package tutorial.pattrens.observer.sxt.v1;

/**
 * @author yuanwei
 * @version ctreateTime:2011-7-14 上午10:27:45
 */
public class Main {
	public static void main(String[] args) {
		new Thread(new Child(new Dad())).start();
	}
}
