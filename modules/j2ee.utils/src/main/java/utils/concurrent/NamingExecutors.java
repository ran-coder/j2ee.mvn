package utils.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author yuanwei
 * @version ctreateTime:2011-6-29 上午09:37:43
 */
public class NamingExecutors {
	/** 一个固定大小的ThreadPool */
	public static ThreadPoolExecutor newFixedThreadPool(String name, int nThreads) {
		return new ThreadPoolExecutor(nThreads,nThreads,0L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),new NamingThreadFactory(name));
	}
	/** 一个固定大小的ThreadPool ArrayBlockingQueue */
	public static ThreadPoolExecutor newFixedThreadPoolArrayBlockingQueue(String name, int nThreads) {
		return newFixedThreadPool(name,nThreads,Integer.MAX_VALUE);
	}
	/** 一个固定大小的ThreadPool ArrayBlockingQueue */
	public static ThreadPoolExecutor newFixedThreadPool(String name, int nThreads,int capacity) {
		return new ThreadPoolExecutor(nThreads,nThreads,0L,TimeUnit.SECONDS,
				//true FIFO;false FILO
				new ArrayBlockingQueue<Runnable>(capacity,false),
				new NamingThreadFactory(name));
	}
	/** 比较适合没有固定大小并且比较快速就能完成的小任务，没必要维持一个Pool，这比直接new Thread来处理的好处是能在60秒内重用已创建的线程 */
	public static ThreadPoolExecutor newCachedThreadPool(String name) {
		return newCachedThreadPool(name,0);
	}
	/** 比较适合没有固定大小并且比较快速就能完成的小任务，没必要维持一个Pool，这比直接new Thread来处理的好处是能在60秒内重用已创建的线程 */
	public static ThreadPoolExecutor newCachedThreadPool(String name,int corePoolSize) {
		return newCachedThreadPool(name,corePoolSize,Integer.MAX_VALUE);
	}
	/** 比较适合没有固定大小并且比较快速就能完成的小任务，没必要维持一个Pool，这比直接new Thread来处理的好处是能在60秒内重用已创建的线程 */
	public static ThreadPoolExecutor newCachedThreadPool(String name,int corePoolSize,long keepAliveTime) {
		return new ThreadPoolExecutor(corePoolSize,Integer.MAX_VALUE,60L,TimeUnit.SECONDS,new SynchronousQueue<Runnable>(),new NamingThreadFactory(name));
	}
	/** 比较适合没有固定大小并且比较快速就能完成的小任务，没必要维持一个Pool，这比直接new Thread来处理的好处是能在60秒内重用已创建的线程 */
	public static ThreadPoolExecutor newCachedThreadPool(String name,int corePoolSize ,int maxPoolSize) {
		return newCachedThreadPool(name,corePoolSize,maxPoolSize,new SynchronousQueue<Runnable>());
	}
	/** 比较适合没有固定大小并且比较快速就能完成的小任务，没必要维持一个Pool，这比直接new Thread来处理的好处是能在60秒内重用已创建的线程 */
	public static ThreadPoolExecutor newCachedThreadPool(String name,int corePoolSize,int maxPoolSize,BlockingQueue<Runnable> workQueue) {
		return new ThreadPoolExecutor(corePoolSize,maxPoolSize,60L,TimeUnit.SECONDS,workQueue,new NamingThreadFactory(name));
	}
	/** 一个固定大小的ThreadPool */
	public static ThreadPoolExecutor newFixedCachedThreadPool(String name, int nThreads,int capacity) {
		return new ThreadPoolExecutor(nThreads,nThreads,60L,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(capacity, false),new NamingThreadFactory(name));
	}
	/** 一个固定大小的ThreadPool,任务队列大小为1000 */
	public static ThreadPoolExecutor newFixedCachedThreadPool(String name, int nThreads) {
		return newFixedCachedThreadPool(name,nThreads,1000);
	}
	public static void execute(ExecutorService executor,Runnable task,int runtime){
		if(runtime<1)return;
		if(executor==null)
			throw new NullPointerException("executor is null!");
		for(int i=0;i<runtime;i++){
			executor.execute(task);
		}
	}
}
