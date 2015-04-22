package utils.test.benchmark;

import utils.Numbers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-19 10:42
 * 任务按照successAvgTime排序
 */
public class RunTaskResult implements Comparable<RunTaskResult>{
	private String name;
	private List<Long> successResults;
	private int successCount;
	private int failCount;
	private double avg;
	private long all;

	public double getAvg(){
		return avg;
	}

	public long getAll(){
		return all;
	}

	public RunTaskResult(CompletionService<Long> service,String name,int taskCount){
		this.name=name;
		int nResults=0;
		successResults=new ArrayList<Long>(taskCount);
		Long result=null;
		while(nResults<taskCount){
			try{
				result=service.take().get();
				//successCount++;
			}catch(InterruptedException e){
				e.printStackTrace();
				failCount++;result=-1L;
			}catch(ExecutionException e){
				e.printStackTrace();
				failCount++;result=-1L;
			}
			if(result!=null&&result>0){
				successResults.add(result);successCount++;
			}else{
				//failCount++;
			}
			nResults++;
		}
		Collections.sort(successResults, new Comparator<Long>(){
			public int compare(Long o1, Long o2){
				if(o1==null || o2==null) return 0;
				if(o1>o2) return 1;
				if(o1<o2) return -1;
				return 0;
			}
		});
		if(successResults!=null&&!successResults.isEmpty()){
			for(long l:successResults){
				all+=l;
			}
			if(successResults.size()!=0)avg=all/( (double)successResults.size());
		}
		System.out.println("task["+name+"] completed.");



	}

	@Override
	public String toString(){
		//时间排序 task[name][success,fail][avg,all,min,max]
		StringBuilder builder=new StringBuilder()
			.append("task[").append(name).append("][success(")
				.append(successCount).append("),fail(").append(failCount).append(")]")
			;

		if(successResults!=null&&!successResults.isEmpty()){
			builder.append('[')
			.append(Numbers.doubleRound(avg, 2)).append(',').append(all).append(',')
			.append(successResults.get(0)).append(',').append(successResults.get(successResults.size()-1))
			//.append(',').append(successResults.size())
			.append(']').append("[avg,all,min,max]")
			;

			return builder.toString();
		}
		return super.toString();    //To change body of overridden methods use File | Settings | File Templates.
	}

	public int compareTo(RunTaskResult o){
		if(o==null) return 0;
		if(this.getAvg()==o.getAvg()) return 0;
		if(this.getAvg()>o.getAvg()) return 1;
		if(this.getAvg()<o.getAvg()) return 1;
		return 0;
	}
}
