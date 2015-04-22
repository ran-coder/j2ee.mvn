package tutorial.apache.util.pool.ibmexample;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.ObjectPoolFactory;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPoolFactory;

public class ObjectPoolFactorySample {
	public static void main(String[] args) {
		Object obj=null;
		PoolableObjectFactory factory=new PoolableObjectFactorySample();
		ObjectPoolFactory poolFactory=new StackObjectPoolFactory(factory);
		ObjectPool pool=poolFactory.createPool();
		try{
			for(long i=0;i < 100;i++){
				System.out.println("== " + i + " ==");
				obj=pool.borrowObject();
				System.out.println(obj);
				pool.returnObject(obj);
			}
			obj=null;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(obj != null){
					pool.returnObject(obj);
				}
				pool.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
