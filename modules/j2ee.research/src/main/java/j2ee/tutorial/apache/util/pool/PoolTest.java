package j2ee.tutorial.apache.util.pool;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;


public class PoolTest {
	public static void main(String[] args) throws Exception {
		//创建一个对象池
		GenericKeyedObjectPool pool=new GenericKeyedObjectPool(new BaseKeyedPoolableObjectFactory() {
			@Override
			public Object makeObject(Object o) throws Exception {
				return o;
			}
		});

		//添加对象到池，重复的不会重复入池
		pool.addObject("a");
		pool.addObject("a");
		pool.addObject("b");
		pool.addObject("x");

		//清除最早的对象
		pool.clearOldest();

		//获取并输出对象
		System.out.println(pool.borrowObject("a"));
		System.out.println(pool.borrowObject("b"));
		System.out.println(pool.borrowObject("c"));
		System.out.println(pool.borrowObject("c"));
		System.out.println(pool.borrowObject("a"));

		//输出池状态
		System.out.println(pool.getMaxIdle());
		System.out.println(pool.getMaxActive());

	}
}