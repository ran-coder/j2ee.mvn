package j2ee.tutorial.thread;

import utils.ClassUtil;
import utils.RandomUtil;

public class ThreadLocalTest {
	public static void test(){
		Runnable run=new Runnable(){
			ThreadLocal<Integer> number=new ThreadLocal<Integer>();
			public void run() {
				number.set(RandomUtil.getRandomInt(500,2500));
				ClassUtil.sleep(number.get());
				System.out.println(number.get());
			}
		};
		for(int i=0;i<5;i++){
			new Thread(run).start();
		}
	}
	public static void main(String[] args) {
		test();
	}
	static class ThreadData{
		ThreadLocal<String> threadLocal=new ThreadLocal<String>();
		public ThreadData(){
			threadLocal.set(Thread.currentThread().getName());
		}
		public String get(){
			return threadLocal.get();
		}
	}
}
