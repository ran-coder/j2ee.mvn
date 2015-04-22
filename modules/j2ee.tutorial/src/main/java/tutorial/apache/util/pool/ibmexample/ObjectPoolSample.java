package tutorial.apache.util.pool.ibmexample;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPool;

public class ObjectPoolSample {
	public static void main(String[] args) {
		Object obj=null;
		PoolableObjectFactory factory=new PoolableObjectFactorySample();
		ObjectPool pool=new StackObjectPool(factory);
		try{
			for(long i=0;i < 100;i++){
				System.out.println("== " + i + " ==");
				obj=pool.borrowObject();
				System.out.println(obj);
				pool.returnObject(obj);
			}
			obj=null;//明确地设为null，作为对象已归还的标志
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(obj != null){//避免将一个对象归还两次
					pool.returnObject(obj);
				}
				pool.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
