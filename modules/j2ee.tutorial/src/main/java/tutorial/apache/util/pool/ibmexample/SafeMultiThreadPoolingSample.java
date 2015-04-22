package tutorial.apache.util.pool.ibmexample;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

class SafePicker extends Thread {
	private ObjectPool	pool;

	public SafePicker(ObjectPool op) {
		pool=op;
	}

	public void run() {
		try{
			/* 略加处理 */
			synchronized(pool){
				if(pool.getNumActive() < 5){
					sleep((long)(Math.random() * 10));
					System.out.println(pool.borrowObject().toString());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

public class SafeMultiThreadPoolingSample {
	public static void main(String[] args) {
		ObjectPool pool=new StackObjectPool(new BasePoolableObjectFactorySample());
		Thread ts[]=new Thread[20];
		for(int j=0;j < ts.length;j++){
			ts[j]=new SafePicker(pool);
			ts[j].start();
		}
		try{
			Thread.sleep(1000);
			System.out.println("NumActive:" + pool.getNumActive());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
