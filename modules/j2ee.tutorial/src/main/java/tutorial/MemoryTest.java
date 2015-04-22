package tutorial;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import javax.management.Notification;
import javax.management.NotificationEmitter;

/**
 * @author yuanwei
 * @version ctreateTime:2011-6-15 下午04:59:38
 */
public class MemoryTest {
	public static void main(String[] args) {
		MemoryMXBean memorymbean=ManagementFactory.getMemoryMXBean();

		MemoryUsage heapMemoryUsage=memorymbean.getHeapMemoryUsage();
		MemoryUsage nonHeapMemoryUsage=memorymbean.getNonHeapMemoryUsage();

		System.out.println(heapMemoryUsage.toString());
		System.out.println(nonHeapMemoryUsage.toString());

		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		NotificationEmitter emitter = (NotificationEmitter) mbean;
		MemoryListener listener = new MemoryListener();
		emitter.addNotificationListener(listener, null, null);
	}

	static class MemoryListener implements javax.management.NotificationListener {
		public void handleNotification(Notification notification, Object handback) {
			System.out.println("MemoryListener");
		}
	}
}
