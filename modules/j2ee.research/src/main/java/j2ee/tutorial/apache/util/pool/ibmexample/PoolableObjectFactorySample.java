package j2ee.tutorial.apache.util.pool.ibmexample;

import org.apache.commons.pool.PoolableObjectFactory;

public class PoolableObjectFactorySample implements PoolableObjectFactory {
	private static int	counter	=0;

	public Object makeObject() throws Exception {
		Object obj=String.valueOf(counter++);
		System.err.println("Making Object " + obj);
		return obj;
	}

	public void activateObject(Object obj) throws Exception {
		System.err.println("Activating Object " + obj);
	}

	public void passivateObject(Object obj) throws Exception {
		System.err.println("Passivating Object " + obj);
	}

	public boolean validateObject(Object obj) {
		/* 以1/2的概率将对象判定为失效 */
		boolean result=(Math.random() > 0.5);
		System.err.println("Validating Object " + obj + " : " + result);
		return result;
	}

	public void destroyObject(Object obj) throws Exception {
		System.err.println("Destroying Object " + obj);
	}
}
