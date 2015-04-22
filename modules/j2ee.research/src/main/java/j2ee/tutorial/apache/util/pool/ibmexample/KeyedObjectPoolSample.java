package j2ee.tutorial.apache.util.pool.ibmexample;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.StackKeyedObjectPoolFactory;

class KeyedPoolableObjectFactorySample extends BaseKeyedPoolableObjectFactory {
	public Object makeObject(Object key) throws Exception {
		return "[" + key.hashCode() + "]";
	}
}

public class KeyedObjectPoolSample {
	public static void main(String[] args) {
		Object obj=null;
		KeyedPoolableObjectFactory factory=new KeyedPoolableObjectFactorySample();
		KeyedObjectPoolFactory poolFactory=new StackKeyedObjectPoolFactory(factory);
		KeyedObjectPool pool=poolFactory.createPool();
		String key=null;
		try{
			for(long i=0;i < 100;i++){
				key=Double.valueOf((Math.random() * 10)).toString();
				System.out.println("== " + i + " ==");
				System.out.println("Key:" + key);
				obj=pool.borrowObject(key);
				System.out.println("Object:" + obj);
				pool.returnObject(key,obj);
				obj=null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(obj != null){
					pool.returnObject(key,obj);
				}
				pool.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
