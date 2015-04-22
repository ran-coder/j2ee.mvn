package utils.concurrent.sync.example;

import utils.ClassUtil;
/**
 * 当一个线程访问object的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。
 * 当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞。
 */
public class SynchronizedThisMethodThread {
	public void sync() {
		synchronized(this){
			int i=5;
			while(i-- > 0){
				System.out.println("sync:"+Thread.currentThread().getName() + " : " + i);
				ClassUtil.sleep(500);
			}
		}
	}
	public void sync1() {
		synchronized(this){
			int i=5;
			while(i-- > 0){
				System.out.println("sync1:"+Thread.currentThread().getName() + " : " + i);
				ClassUtil.sleep(500);
			}
		}
	}
	public static void syncStatic() {
		synchronized(SynchronizedThisMethodThread.class){//等同public static synchronized void syncStatic
			int i=5;
			while(i-- > 0){
				System.out.println("sync1:"+Thread.currentThread().getName() + " : " + i);
				ClassUtil.sleep(500);
			}
		}
	}
	public void noSync() {
		int i=5;
		while(i-- > 0){
			System.out.println(Thread.currentThread().getName() + " : " + i);
			ClassUtil.sleep(500);
		}
	}

	public static void main(String[] args) {
		final SynchronizedThisMethodThread clazz=new SynchronizedThisMethodThread();
		new Thread(new Runnable() {
			public void run() {
				clazz.sync();
			}
		},"t1").start();
		new Thread(new Runnable() {
			public void run() {
				clazz.noSync();
			}
		},"t2").start();
		new Thread(new Runnable() {
			public void run() {
				clazz.sync1();
			}
		},"t3").start();
		new Thread(new Runnable() {
			public void run() {
				new SynchronizedThisMethodThread().sync();
			}
		},"t4").start();
		new Thread(new Runnable() {
			public void run() {
				SynchronizedThisMethodThread.syncStatic();
			}
		},"t5").start();
	}
}