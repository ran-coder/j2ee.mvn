package tutorial.pattrens.observer.sun;

/**
 * @author yuanwei
 * @version ctreateTime:2011-5-6 下午02:00:31
 */

public class Tester {

	public static void main(String[] args) {
		Watched	watched=new Watched();

		new Watcher(watched);

		watched.changeData("In C, we create bugs.");
		watched.changeData("In Java, we inherit bugs.");
		watched.changeData("In Java, we inherit bugs.");
		watched.changeData("In Visual Basic, we visualize bugs.");
	}
}
