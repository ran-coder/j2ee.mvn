package j2ee.tutorial.apache.util.pool;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;

import utils.config.ConfigUtil;

public class PoolUtil {
	public static GenericKeyedObjectPool.Config initGenericKeyedObjectPoolConfig() {
		ConfigUtil util=new ConfigUtil("pool");
		GenericKeyedObjectPool.Config cfg=new GenericKeyedObjectPool.Config();
		cfg.lifo=Boolean.valueOf(util.get("lifo","true"));
		cfg.maxActive=Integer.valueOf(util.get("maxActive","18"));
		cfg.maxIdle=Integer.valueOf(util.get("maxIdle","6"));
		cfg.maxWait=Integer.valueOf(util.get("maxWait","150000"));
		cfg.minEvictableIdleTimeMillis=Integer.valueOf(util.get("minEvictableIdleTimeMillis","100000"));
		cfg.minIdle=Integer.valueOf(util.get("minIdle","0"));
		cfg.numTestsPerEvictionRun=Integer.valueOf(util.get("numTestsPerEvictionRun","1"));
		cfg.lifo=Boolean.valueOf(util.get("testOnBorrow","false"));
		cfg.lifo=Boolean.valueOf(util.get("testOnReturn","false"));
		cfg.lifo=Boolean.valueOf(util.get("testWhileIdle","false"));
		cfg.timeBetweenEvictionRunsMillis=Integer.valueOf(util.get("timeBetweenEvictionRunsMillis","120000"));
		cfg.whenExhaustedAction=Byte.valueOf(util.get("whenExhaustedAction","1"));
		return cfg;
	}
}
