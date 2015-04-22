package tutorial.thread.socket;

public class PoolManager { //线程池管理器，系统应保证只有一个实例，应用单例模式

	private static PoolManager instance = null; 
//	构造函数为私有的，保证外部类不可以实例化该类
	private PoolManager(){
		
	}
	public static PoolManager getInstance(){
		if(instance==null)instance = new PoolManager();
		return instance;
	}
	
	//创建线程池实例方法
	public ThreadPool createThreadPool(int max,Class<?extends Worker> worker){
		ThreadPool threadPool = null;
		try {
			threadPool = new ThreadPool(max,worker); //创建线程池实例
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return threadPool;
	}
}