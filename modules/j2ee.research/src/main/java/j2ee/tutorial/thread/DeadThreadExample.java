package j2ee.tutorial.thread;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-13 下午2:26:29
 *   死锁:两个线程都要对方持有的锁,互相等待,产生阻塞
 */
public class DeadThreadExample {
	static final long SLEEP=5000;
	private Object object1=new Object(),object2=new Object();
	public void test1() {
		synchronized(object1){
			System.out.println(Thread.currentThread().getName()+"get lock:object1 ");
			try{
				Thread.sleep(SLEEP);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			synchronized(object2){
				System.out.println(Thread.currentThread().getName()+".get lock:object2 ");
				try{
					Thread.sleep(SLEEP);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+".release lock:object2");
			}
			System.out.println(Thread.currentThread().getName()+".release lock:object1");
		}
	}

	public void test2() {
		synchronized(object2){
			System.out.println(Thread.currentThread().getName()+".get lock:object2 ");
			try{
				Thread.sleep(SLEEP);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			synchronized(object1){
				System.out.println(Thread.currentThread().getName()+".get lock:object1 ");
				try{
					Thread.sleep(SLEEP);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+".release lock:object1");
			}
			System.out.println(Thread.currentThread().getName()+".release lock:object2");
		}
	}
	public static void main(String[] args) {
		final DeadThreadExample ex=new DeadThreadExample();
		Thread thread1=new Thread(new Runnable() {
			public void run() {
				int i=0;
				while(i<5){
					ex.test1();
					i++;
				}
			}
		},"thread1");
		Thread thread2=new Thread(new Runnable() {
			public void run() {
				int i=0;
				while(i<5){
					ex.test2();
					i++;
				}
			}
		},"thread2");
		thread1.start();
		thread2.start();
	}
}
