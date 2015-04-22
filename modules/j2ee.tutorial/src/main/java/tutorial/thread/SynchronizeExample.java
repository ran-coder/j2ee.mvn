package tutorial.thread;

/**
 * @author yuanwei
 * @version ctreateTime:2011-6-29 上午11:29:34
 */
public class SynchronizeExample {
	static final long SLEEP=5000;
	private Object object=new Object();
	public void test1() {
		synchronized(object){
			System.out.println(Thread.currentThread().getName()+":test1");
			try{
				Thread.sleep(SLEEP);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+":test1 end");
		}
	}

	public void test2() {
		synchronized(object){
			System.out.println(Thread.currentThread().getName()+":test2");
			try{
				Thread.sleep(SLEEP);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+":test2 end");
		}
	}

	public synchronized void test3() {
		System.out.println(Thread.currentThread().getName()+":test3");
		try{
			Thread.sleep(SLEEP);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+":test3 end");
	}
	
	public  void test4() {
		System.out.println(Thread.currentThread().getName()+":test4");
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+":test4 end");
	}
	public static void main(String[] args) {
		final SynchronizeExample syn=new SynchronizeExample();
		Thread thread1=new Thread(new Runnable() {
			public void run() {
				int i=0;
				while(i<5){
					//System.out.println(Thread.currentThread().getName()+": before");
					syn.test1();
					//System.out.println(Thread.currentThread().getName()+": after");
					i++;
				}
			}
		},"thread1");
		Thread thread2=new Thread(new Runnable() {
			public void run() {
				int i=0;
				while(i<5){
					//System.out.println(Thread.currentThread().getName()+":before");
					syn.test4();
					//System.out.println(Thread.currentThread().getName()+":after");
					i++;
				}
			}
		},"thread2");
		thread1.start();
		thread2.start();
	}
	

}
