package j2ee.server.jmemcached;

import java.net.InetSocketAddress;

import com.thimbleware.jmemcached.CacheImpl;
import com.thimbleware.jmemcached.Key;
import com.thimbleware.jmemcached.LocalCacheElement;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.CacheStorage;
import com.thimbleware.jmemcached.storage.hash.ConcurrentLinkedHashMap;

/**
 * @author yuanwei
 * @version ctreateTime:2012-7-23 下午3:05:42
 */
public class JmemcachedMain {
	/**
	 * http://code.google.com/p/jmemcache-daemon/
	 *  -b,--binary              binary protocol mode
		 -bs,--block-size <arg>   block size (in bytes) for external memory mapped
		                          file allocator.  default is 8 bytes
		 -c,--ceiling <arg>       ceiling memory to use; in bytes, specify K, kb,
		                          M, GB for larger units
		 -f,--mapped-file <arg>   use external (from JVM) heap through a memory
		                          mapped file
		 -h,--help                print this help screen
		 -i,--idle <arg>          disconnect after idle <x> seconds
		 -l,--listen <arg>        Address to listen on
		 -m,--memory <arg>        max memory to use; in bytes, specify K, kb, M,
		                          GB for larger units
		 -p,--port <arg>          port to listen on
		 -s,--size <arg>          max items
		 -v                       verbose (show commands)
		 -V                       Show version number
	 */
	static void run(){
		int maxItems=100000;
		long maxBytes=1L*1024*1024*20;//20M
		int idleTime=180;
		boolean binary=true;
		boolean verbose=true;
		InetSocketAddress addr=new InetSocketAddress(3301);
		// create daemon and start it
		final MemCacheDaemon<LocalCacheElement> daemon=new MemCacheDaemon<LocalCacheElement>();
		CacheStorage<Key, LocalCacheElement> storage=ConcurrentLinkedHashMap.create(ConcurrentLinkedHashMap.EvictionPolicy.FIFO,maxItems,maxBytes);
		daemon.setCache(new CacheImpl(storage));
		daemon.setBinary(binary);
		daemon.setAddr(addr);
		daemon.setIdleTime(idleTime);
		daemon.setVerbose(verbose);
		daemon.start();
	}
	public static void main(String[] args) {
		run();
	}
}
