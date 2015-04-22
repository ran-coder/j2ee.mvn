package tutorial.apache.util.pool.ibmexample;

import org.apache.commons.pool.BasePoolableObjectFactory;

public class BasePoolableObjectFactorySample extends BasePoolableObjectFactory {
	private int	counter	=0;

	public Object makeObject() throws Exception {
		return String.valueOf(counter++);
	}
}
