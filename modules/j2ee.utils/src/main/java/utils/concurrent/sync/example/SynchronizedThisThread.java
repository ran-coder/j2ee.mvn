package utils.concurrent.sync.example;

import utils.ClassUtil;
/**
 * 当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，
 * 一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
 */
public class SynchronizedThisThread implements Runnable {
	public void run() {
		synchronized(this){
			for(int i=0;i < 5;i++){
				System.out.println(Thread.currentThread().getName() + " synchronized loop " + i);
				ClassUtil.sleep(800);
			}
		}
	}

	public static void main(String[] args) {
		SynchronizedThisThread t1=new SynchronizedThisThread();
		Thread ta=new Thread(t1,"A");
		Thread tb=new Thread(t1,"B");
		ta.start();
		tb.start();
	}
}