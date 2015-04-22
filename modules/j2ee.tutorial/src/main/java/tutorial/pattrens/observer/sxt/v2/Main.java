package tutorial.pattrens.observer.sxt.v2;

/**
 * @author yuanwei
 * @version ctreateTime:2011-7-14 上午10:27:45
 */
public class Main {
	public static void main(String[] args) {
		Baby baby=new Baby();
		baby.addWeakUpListener(new Dad());
		baby.addWeakUpListener(new GrandFather());
		new Thread(baby).start();
	}
}
