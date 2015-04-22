package utils.concurrent;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import utils.DateUtil;

/**  
 * @author yuanwei  
 * @version ctreateTime:2013-1-7 下午2:59:38
 *   
 */
public class Scheduleds {
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor executor=new ScheduledThreadPoolExecutor(1);
		executor.schedule(new Runnable() {
			public void run() {
				while(true){
					System.out.println(DateUtil.toString(new Date()));
					try{
						Thread.sleep(10000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		},10,TimeUnit.SECONDS);//10s后执行一次
	}
}
