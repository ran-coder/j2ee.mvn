package tutorial.apache.util.pool;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.BasePoolableObjectFactory;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @author leizhimin 2009-10-28 10:51:11
 */
public class GenericObjectPoolTest {
	public static void main(String[] args) throws Exception {
		final Random random=new Random();
		GenericObjectPool pool=new GenericObjectPool(new BasePoolableObjectFactory() {
			public Object makeObject() throws Exception {
				return random.nextFloat();
			}
		});

		pool.addObject();
		pool.addObject();
		pool.addObject();
		pool.addObject();
		pool.addObject();
		pool.addObject();
		pool.addObject();
		pool.addObject();
		pool.addObject();
		pool.addObject();
		pool.addObject();
		pool.addObject();

		Object o=pool.borrowObject();
		System.out.println(o);
		pool.returnObject(o);

		Object o1=pool.borrowObject();
		System.out.println(o1);

		Object o2=pool.borrowObject();
		System.out.println(o2);

		Object o3=pool.borrowObject();
		System.out.println(o3);

		Object o4=pool.borrowObject();
		System.out.println(o4);

		Object o5=pool.borrowObject();
		System.out.println(o5);

		Object o6=pool.borrowObject();
		System.out.println(o6);

		Object o7=pool.borrowObject();
		System.out.println(o7);

		Object o8=pool.borrowObject();
		System.out.println(o8);

		pool.returnObject(o1);
		pool.returnObject(o2);
		pool.returnObject(o3);
		pool.returnObject(o4);
		pool.returnObject(o5);
		pool.returnObject(o6);
		pool.returnObject(o7);
		pool.returnObject(o8);

		Object o9=pool.borrowObject();
		System.out.println(o9);
		pool.returnObject(o9);

		Object o10=pool.borrowObject();
		System.out.println(o10);
		pool.returnObject(o10);

		Object o11=pool.borrowObject();
		System.out.println(o11);
		pool.returnObject(o11);

		Object o12=pool.borrowObject();
		System.out.println(o12);
		pool.returnObject(o12);

		Object o13=pool.borrowObject();
		System.out.println(o13);
		pool.returnObject(o13);

	}
}