package utils.test.benchmark;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-19 10:42
 * To change this template use File | Settings | File Templates.
 */
public class Worker implements Callable<Long>{
	private Runnable task;

	public Worker(Runnable task){
		this.task=task;
	}

	public Long call() throws Exception{
		long start=System.nanoTime();
		task.run();
		return System.nanoTime()-start;
	}
}
