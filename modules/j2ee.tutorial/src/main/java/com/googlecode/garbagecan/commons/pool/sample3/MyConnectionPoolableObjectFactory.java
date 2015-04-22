package com.googlecode.garbagecan.commons.pool.sample3;

import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyConnectionPoolableObjectFactory implements PoolableObjectFactory<MyConnection> {

	private static Logger logger = LoggerFactory.getLogger(MyConnectionPoolableObjectFactory.class);
	
	private static int count = 0;
	
	public MyConnection makeObject() throws Exception {
		MyConnection myConn = new MyConnection(generateName());
		logger.info("Make object " + myConn.getName());
		myConn.connect();
		return myConn;
	}
	
	public void activateObject(MyConnection obj) throws Exception {
		MyConnection myConn = (MyConnection)obj;
		logger.info("Activate object " + myConn.getName());
	}

	public void passivateObject(MyConnection obj) throws Exception {
		MyConnection myConn = (MyConnection)obj;
		logger.info("Passivate object " + myConn.getName());
	}
	
	public boolean validateObject(MyConnection obj) {
		MyConnection myConn = (MyConnection)obj;
		logger.info("Validate object " + myConn.getName());
		return myConn.isConnected();
	}
	
	public void destroyObject(MyConnection obj) throws Exception {
		MyConnection myConn = (MyConnection)obj;
		logger.info("Destroy object " + myConn.getName());
		myConn.close();
	}
	
	private synchronized String generateName() {
		return "conn_" + (++count);
	}
}
