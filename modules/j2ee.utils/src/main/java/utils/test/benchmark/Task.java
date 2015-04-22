package utils.test.benchmark;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-19 10:15
 * To change this template use File | Settings | File Templates.
 */
public abstract class Task implements Runnable{
	private String name;
	public Task(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public abstract void run();
}
