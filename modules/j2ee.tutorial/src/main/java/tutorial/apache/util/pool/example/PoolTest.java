package tutorial.apache.util.pool.example;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPool;
/**
 * @see http://www.blogjava.net/changedi/archive/2011/05/06/349665.html
 */
public class PoolTest {
	public static void main(String[] args) {
		BigObject bo=null;
		PoolableObjectFactory factory=new BigObjectFactory();
		ObjectPool pool=new StackObjectPool(factory);
		try{
			for(long i=0;i < 10;i++){
				System.out.println("== " + i + " ==");
				bo=(BigObject)pool.borrowObject();
				System.out.println(bo +"	pool.getNumActive()="+pool.getNumActive());
				if((i & 1) == 0){
					pool.returnObject(bo);
				}
			}
			// bo = null;//明确地设为null，作为对象已归还的标志
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(bo != null){// 避免将一个对象归还两次
					pool.returnObject(bo);
				}
				pool.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}