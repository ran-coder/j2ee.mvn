package j2ee.tutorial.thread;

import java.util.concurrent.CountDownLatch;
/**
 * 能创建的线程数的具体计算公式如下：
 * (MaxProcessMemory - JVMMemory - ReservedOsMemory) / (ThreadStackSize) = Number of threads 
 * MaxProcessMemory 指的是一个进程的最大内存
 * JVMMemory         JVM内存
 * ReservedOsMemory  保留的操作系统内存
 * ThreadStackSize      线程栈的大小
 * @see http://sesame.iteye.com/blog/622670
 * MaxProcessMemory 在32位的 windows下是 2G
 * JVMMemory   eclipse默认启动的程序内存是64M
 * ReservedOsMemory  一般是130M左右
 * ThreadStackSize 32位 JDK 1.6默认的stacksize 325K左右
 * 公式如下：
 * (2*1024*1024-64*1024-130*1024)/325 = 5841
 * 公式计算所得5841，和实践5602基本一致（有偏差是因为ReservedOsMemory不能很精确）
 * 
 */
public class TestNativeOutOfMemoryError {
	public static void main(String[] args) {
		for(int i=0;;i++){
			System.out.println("i = " + i);
			new Thread(new HoldThread()).start();
		}
	}

}

class HoldThread extends Thread {
	CountDownLatch	cdl	=new CountDownLatch(1);

	public HoldThread() {
		this.setDaemon(true);
	}

	public void run() {
		try{
			cdl.await();
		}catch(InterruptedException e){
		}
	}
}