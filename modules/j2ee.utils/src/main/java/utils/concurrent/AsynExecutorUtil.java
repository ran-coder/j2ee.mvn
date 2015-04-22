package utils.concurrent;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;


/**
 * @author yuanwei
 * @version ctreateTime:2011-6-24 上午09:20:47
 */
public class AsynExecutorUtil {
	public static void execute(String name,int nThreads, int count,Runnable task){
		ExecutorService executor=NamingExecutors.newFixedThreadPool("AsynExecutorUtil",nThreads);
		CompletionService<Long>	service=new ExecutorCompletionService<Long>(executor);
		Worker worker=new Worker(task);
		for(int i=0;i<count;i++){
			if(!executor.isShutdown()){
				service.submit(worker);
			}
		}
		
		int nResults=0;
		long[] times=new long[count];
		Long result=0L;
		while(nResults<count){
			try{
				result=service.take().get();
			}catch(InterruptedException e){
				e.printStackTrace();
			}catch(ExecutionException e){
				e.printStackTrace();
			}
			times[nResults]=(result==null?0L:result);
			nResults++;
		}
		Arrays.sort(times);

		if(executor != null){
			executor.shutdown();
			if(executor.isShutdown() || executor.isTerminated()){
				executor=null;
			}
		}
		service=null;

		if(times!=null){
			long all=0L;
			for(int i=0;i<count;i++){
				all+=times[i];
			}
			StringBuilder sb=new StringBuilder(name);
			sb.append(":avg[").append(all/( (double)count)).append("] ")
			.append("min[").append(times[0]).append("] ")
			.append("max[").append(times[count-1]).append("] (ms)")
			.append("\n").append(Arrays.toString(times))
			;
			System.out.println(sb.toString());
		}
	}
	public static long getRandomLong(long min, long max) {
		return min + (long)( Math.random() * (max - min) );
	}
	private static class Worker implements Callable<Long>{
		private Runnable task;
		public Worker(Runnable task){
			this.task=task;
		}

		public Long call() throws Exception {
			long start=System.currentTimeMillis();
			task.run();
			return System.currentTimeMillis()-start;
		}
	}

	public static void main(String[] args) {
		Runnable task=new Runnable() {
			public void run() {
				try{
					Thread.sleep(getRandomLong(100,200));
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		};

		AsynExecutorUtil.execute("static task",5,20,task);
	}
}
