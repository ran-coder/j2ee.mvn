package j2ee.research.java.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import utils.ClassUtil;
import utils.concurrent.NamingExecutors;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-7-25 下午2:42:01
 *   
 */
public class ThreadPoolExecutorExample {
	final static int nThreads=10;
	static void stat2(ThreadPoolExecutor executor){
		
	}
	static void runFixedThreadPool(){
		//Executors.newFixedThreadPool(nThreads,new NamingThreadFactory("fixedThreadPool"));
		ExecutorService executor=NamingExecutors.newFixedCachedThreadPool("fixedThreadPool",nThreads);
		//new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),new NamingThreadFactory("fixedThreadPool"));
		System.out.println(executor.toString());
		Task task=new Task();
		for(int i=0;i < 15;i++)executor.execute(task);ClassUtil.sleep(executor.toString(),61000L);

		for(int i=0;i < 10;i++)executor.execute(task);ClassUtil.sleep(executor.toString(),61000L);

		//while(!executor.isShutdown()){
		//	ClassUtil.sleep("runFixedThreadPool",10000L);System.out.println(executor);
		//}
	}
	static void runCachedThreadPool(){
		//new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>(),new NamingThreadFactory("fixedThreadPool"))
		ExecutorService executor=NamingExecutors.newCachedThreadPool("fixedThreadPool",nThreads,20L);
		System.out.println(executor.toString());
		Task task=new Task();
		for(int i=0;i < 15;i++)executor.execute(task);ClassUtil.sleep(executor.toString(),21000L);
		for(int i=0;i < 20;i++)executor.execute(task);ClassUtil.sleep(executor.toString(),21000L);
		for(int i=0;i < 10;i++)executor.execute(task);ClassUtil.sleep(executor.toString(),21000L);

	}
	
	static class Task implements Runnable{
		@Override
		public void run() {
			ClassUtil.sleepInline(1000L);
		}
		
	}
	public static void main(String[] args) {
		runCachedThreadPool();
	}
}
