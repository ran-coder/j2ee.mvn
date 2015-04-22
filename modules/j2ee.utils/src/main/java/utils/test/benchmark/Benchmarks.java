package utils.test.benchmark;

import org.apache.commons.lang.math.RandomUtils;
import utils.AssertUtil;
import utils.concurrent.NamingExecutors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-19 10:13
 * add Benchmark.execute(int nThreads,int count,boolean useNano,String[] name,Runnable[] task)
 * 时间排序 task[SingletonByInnerStaticClass] [avg,min,max,all]
 */
public class Benchmarks{
	public static void runTask(int nThreads,int count, TimeUnit timeUnit,Task... tasks){

		AssertUtil.isTrue( !(tasks==null||tasks.length<1),"tasks mast be not empty.");
		ExecutorService executor=NamingExecutors.newFixedThreadPool("ExecutorService_Benchmarks_runtime", nThreads);
		CompletionService<Long> service=new ExecutorCompletionService<Long>(executor);


		List<RunTaskResult> results=new ArrayList<RunTaskResult>();
		for(Task task:tasks){
			Worker worker=new Worker(task);
			for(int i=0;i<count;i++){
				if(!executor.isShutdown()){
					service.submit(worker);
				}
			}
			results.add(new RunTaskResult(service,task.getName(),count));

		}

		if(results!=null&& !results.isEmpty()){
			Collections.sort(results, new Comparator<RunTaskResult>(){
				public int compare(RunTaskResult o1, RunTaskResult o2){
					if(o1==null || o2==null) return 0;
					double d1=o1.getAvg();
					double d2=o2.getAvg();

					if(d1==d2) return 0;
					if(d1>d2) return 1;
					if(d1<d2) return -1;
					return 0;
				}
			});
			for(RunTaskResult result:results){
				System.out.println(result);
			}
		}

		if(executor != null){
			executor.shutdown();
			if(executor.isShutdown() || executor.isTerminated());
		}
	}


	public static void main(String[] args){
		runTask(5,10000,null,new Task("task1"){
			@Override
			public void run(){
				getClass();
			}
		},new Task("task2"){
			@Override
			public void run(){
				getName();
			}
		},new Task("Thread.sleep"){
			@Override
			public void run(){
				try{
					Thread.sleep(RandomUtils.nextInt(10));
				}catch(InterruptedException e){
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}
		});
	}
}
