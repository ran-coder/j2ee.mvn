package utils.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-6-23 下午02:40:582011  
 *   
 */
@Deprecated
public class AsynExecutorServices {
	private ExecutorService			executorService;
	private CountDownLatch countDownLatch;
	private int nThreads;
	private String label;

	public AsynExecutorServices(String label) {
		this.label=label;
	}
	public static AsynExecutorServices newInstance(String label){
		return new AsynExecutorServices(label);
	}
	public AsynExecutorServices newExecutor(ExecutorService executor){
		this.executorService=executor;
		return this;
	}
	public AsynExecutorServices nThreads(int nThreads){
		this.nThreads=nThreads;
		if(this.executorService==null){
			this.executorService=NamingExecutors.newFixedThreadPool(this.label,nThreads);
		}
		//this.completionService=new ExecutorCompletionService<Long>(executor);
		return this;
	}
	public AsynExecutorServices count(int count){
		this.countDownLatch=new CountDownLatch(count);
		return this;
	}
	public void exucute(Runnable task){
		Worker worker=new Worker(countDownLatch,task);
		for(int i=0;i<nThreads;i++){
			if(!executorService.isShutdown()){
				executorService.execute(worker);
			}
		}
		executorService.shutdown();
		try{
			countDownLatch.await();
		}catch(InterruptedException e){
			e.printStackTrace();
			Thread.interrupted();
		}
		if(executorService.isShutdown()){
			System.out.println("isShutdown");
		}
	}
	private class Worker implements Runnable{
		private Runnable task;
		public Worker(CountDownLatch countDownLatch,Runnable task){
			this.task=task;
		}

		public void run() {
			while(true){
				if(countDownLatch.getCount()<1)break;
				long start=System.currentTimeMillis();
				task.run();
				System.out.println(System.currentTimeMillis()-start);
				countDownLatch.countDown();
			}
		}
		
	}
	public static void main(String[] args) {
		AsynExecutorServices.newInstance("test").count(20).nThreads(10).exucute(new Runnable() {
			public void run() {
				//System.out.println("run");
				try{
					Thread.sleep(6000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		});
	}
}
