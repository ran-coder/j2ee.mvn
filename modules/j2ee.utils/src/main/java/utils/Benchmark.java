package utils;

import utils.concurrent.NamingExecutors;
import utils.net.HttpUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;


/**
 * @author yuanwei
 * @version ctreateTime:2011-6-24 上午09:20:47
 */
@Deprecated
public class Benchmark {
	public static void runtime(int nThreads,int count,boolean useNano,String name,Runnable task){
		ExecutorService executor=NamingExecutors.newFixedThreadPool("CompleteExecutorServiceThread",nThreads);
		CompletionService<Long>	service=new ExecutorCompletionService<Long>(executor);
		
		Worker worker=new Worker(task,useNano);
		for(int i=0;i<count;i++){
			if(!executor.isShutdown()){
				service.submit(worker);
			}
		}

		int nResults=0;
		List<Long> list=new ArrayList<Long>(count);
		Long result=null;
		while(nResults<count){
			try{
				result=service.take().get();
			}catch(InterruptedException e){
				e.printStackTrace();
			}catch(ExecutionException e){
				e.printStackTrace();
			}
			if(result!=null){
				list.add(result);
			}
			nResults++;
		}
		Collections.sort(list,new Comparator<Long>() {
			public int compare(Long o1, Long o2) {
				if(o1==null||o2==null)return 0;
				if(o1>o2)return 1;
				if(o1<o2)return -1;
				return 0;
			}
			
		});

		if(!list.isEmpty()){
			long all=0L;
			for(long l:list){
				all+=l;
			}
			StringBuilder sb=new StringBuilder()
			.append("[avg,min,max,all]=[").append(all/( (double)list.size())).append("	")
			.append(list.get(0)).append("	")
			.append(list.get(list.size()-1)).append("	")
			.append(all).append('/').append(list.size())
			.append("]:").append(name)
			//.append("\n").append(Arrays.toString(times))
			;
			System.out.println(sb.toString());
		}
		
		if(executor != null){
			executor.shutdown();
			if(executor.isShutdown() || executor.isTerminated()){
				executor=null;
			}
		}
		service=null;
	}
	public static void runtime(String name,Runnable task,int count,boolean useNano,ExecutorService executor,CompletionService<Long>	service){
		Worker worker=new Worker(task,useNano);
		for(int i=0;i<count;i++){
			if(!executor.isShutdown()){
				service.submit(worker);
			}
		}

		int nResults=0;
		List<Long> list=new ArrayList<Long>(count);
		Long result=null;
		while(nResults<count){
			try{
				result=service.take().get();
			}catch(InterruptedException e){
				e.printStackTrace();
			}catch(ExecutionException e){
				e.printStackTrace();
			}
			if(result!=null){
				list.add(result);
			}
			nResults++;
		}
		Collections.sort(list,new Comparator<Long>() {
			public int compare(Long o1, Long o2) {
				if(o1==null||o2==null)return 0;
				if(o1>o2)return 1;
				if(o1<o2)return -1;
				return 0;
			}
			
		});

		if(!list.isEmpty()){
			long all=0L;
			for(long l:list){
				all+=l;
			}
			StringBuilder sb=new StringBuilder()
			.append("[avg,min,max,all]=[").append(all/( (double)list.size())).append("	")
			.append(list.get(0)).append("	")
			.append(list.get(list.size()-1)).append("	")
			.append(all).append('/').append(list.size())
			.append("]:").append(name)
			//.append("\n").append(Arrays.toString(times))
			;
			System.out.println(sb.toString());
		}
	}
	public static void execute(int nThreads,int count,boolean useNano,String name,Runnable task){
		execute(nThreads,count,useNano,new String[]{name},task);
	}
	public static void execute(int nThreads, int count,boolean useNano,String[] taskNames,Runnable... tasks){
		ExecutorService executor=NamingExecutors.newFixedThreadPool("Benchmark",nThreads);
		CompletionService<Long>	service=new ExecutorCompletionService<Long>(executor);
		for(int i=0,j=taskNames.length;i<j;i++){
			runtime(taskNames[i],tasks[i],count,useNano,executor,service);
		}
		if(executor != null){
			executor.shutdown();
			if(executor.isShutdown() || executor.isTerminated()){
				executor=null;
			}
		}
		service=null;
	}

	private static class Worker implements Callable<Long>{
		private Runnable task;
		private boolean nano;
		public Worker(Runnable task,boolean nano){
			this.task=task;
			this.nano=nano;
		}

		public Long call() throws Exception {
			if(nano){
				long start=System.nanoTime();
				task.run();
				return System.nanoTime()-start;
			}else{
				long start=System.currentTimeMillis();
				task.run();
				return System.currentTimeMillis()-start;
			}
			
		}
	}

	public static void main(String[] args) throws Exception {
		Runnable task=new Runnable() {
			public void run() {
				try{
					//System.out.println(Thread.currentThread().getName());
					HttpUtil.sendByGetHttpClient("http://www.baidu.com",null);
					Thread.sleep(RandomUtil.getRandomInt(100,2000));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		//Benchmark.newInstance("test",5,20).execute(task).completed().printAndClose();
		Benchmark.execute(5,6,false,"static task",task);

		Thread.sleep(RandomUtil.getRandomInt(100,2000));

		Benchmark.execute(5,6,false,"static task",task);
	}
}
