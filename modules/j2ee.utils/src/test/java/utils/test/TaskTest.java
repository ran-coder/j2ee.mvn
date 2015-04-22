package utils.test;

import utils.test.benchmark.Task;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-19 10:21
 * To change this template use File | Settings | File Templates.
 */
public class TaskTest{
	public void testRun() throws Exception{
		new Task("name"){
			@Override
			public void run(){
				//To change body of implemented methods use File | Settings | File Templates.
			}
		};
	}
}
