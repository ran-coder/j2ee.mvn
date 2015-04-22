package utils.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamingThreadFactory implements ThreadFactory {
	static final AtomicInteger	poolNumber		=new AtomicInteger(1);
	final ThreadGroup			group;
	final AtomicInteger			threadNumber	=new AtomicInteger(1);
	final String				namePrefix;

	public NamingThreadFactory(String name) {
		SecurityManager s=System.getSecurityManager();
		group=(s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix=name + "-pt-" + poolNumber.getAndIncrement()+ "-";;
	}

	public NamingThreadFactory() {
		SecurityManager s=System.getSecurityManager();
		group=(s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix="pt-" + poolNumber.getAndIncrement() + "-";
	}

	public Thread newThread(Runnable r) {
		Thread t=new Thread(group,r,namePrefix + threadNumber.getAndIncrement(),0);
		if(t.isDaemon()) t.setDaemon(false);
		if(t.getPriority() != Thread.NORM_PRIORITY) t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}
}
