package j2ee.tutorial.apache.util.pool.ibmexample;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

class UnsafePicker extends Thread {
	private ObjectPool	pool;

	public UnsafePicker(ObjectPool op) {
		pool=op;
	}

	public void run() {
		try{
			/* 似乎…… */
			if(pool.getNumActive() < 5){
				sleep((long)(Math.random() * 10));
				System.out.println(pool.borrowObject());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

public class UnsafeMultiThreadPoolingSample {
	public static void main(String[] args) {
		ObjectPool pool=new StackObjectPool(new BasePoolableObjectFactorySample());
		Thread ts[]=new Thread[20];
		for(int j=0;j < ts.length;j++){
			ts[j]=new UnsafePicker(pool);
			ts[j].start();
		}
		try{
			Thread.sleep(1000);
			/* 然而…… */
			System.out.println("NumActive:" + pool.getNumActive());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
