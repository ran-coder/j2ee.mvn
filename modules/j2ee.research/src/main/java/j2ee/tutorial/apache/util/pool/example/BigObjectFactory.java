package j2ee.tutorial.apache.util.pool.example;

import org.apache.commons.pool.*;

/**
 * @author Administrator
 */

public class BigObjectFactory implements PoolableObjectFactory {

	public void activateObject(Object arg0) throws Exception {
		((BigObject)arg0).setActive(true);
	}

	public void destroyObject(Object arg0) throws Exception {
		arg0=null;
	}

	public Object makeObject() throws Exception {
		BigObject bo=new BigObject();
		return bo;
	}

	public void passivateObject(Object arg0) throws Exception {
		((BigObject)arg0).setActive(false);
	}

	public boolean validateObject(Object arg0) {
		if(((BigObject)arg0).isActive()) return true;
		else return false;
	}

}