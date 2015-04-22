package tutorial.thread.socket;

import java.util.Stack;

public class ThreadPool {

	/*
	 * 任务处理类，该类被线程池调用
	 */
	public static class WorkerThread extends Thread {
		private Worker	worker;
		private Object	data;

		/*
		 * 任务处理类的构造函数
		 * @param id 线程名称
		 * @param worker 与该类关联的工作类
		 */

		public WorkerThread(String id, Worker worker) {
			super();
			this.worker=worker;
			this.data=null;
		}

		@Override
		synchronized public void run() {
			boolean stop=false;
			while(!stop){
				if(data == null){
					try{
						wait();
					}catch(InterruptedException e){
						e.printStackTrace();
						continue;
					}
				}
				if(data != null){
					System.out.println(this.getName());
					worker.run(data);
				}
				data=null;
				//stop=!(push(this));
				stop=true;
			}
		}

		// 唤醒线程并作相应的处理工作
		synchronized public void wake(Object data) {
			this.data=data;
			notify();

		}

	}

	private Stack<WorkerThread>			waiting;		// 就绪线程栈
	private int						max;			// 最大线程数
	private Class<? extends Worker>	workerClass;	// 工作类

	public ThreadPool(int max, Class<? extends Worker> workerClass) throws InstantiationException, IllegalAccessException {
		this.max=max;
		this.waiting=new Stack<WorkerThread>();
		this.workerClass=workerClass;

		Worker worker;
		WorkerThread workerThread;

		for(int i=0;i < max;i++){
			worker=workerClass.newInstance();
			workerThread=new WorkerThread("Worker#" + i,worker);
			workerThread.start();
			waiting.push(workerThread); // 进栈
		}

	}

	// 将处理完任务的线程归还给线程池
	public boolean push(WorkerThread workerThreadw) {
		boolean stayAround=false;
		synchronized(waiting){
			if(waiting.size() < max){
				stayAround=true;
				waiting.push(workerThreadw);
			}
		}
		return stayAround;
	}

	public void performWork(Object data) { // 执行处理客户端请求的任务

		WorkerThread w=null;
		synchronized(waiting){
			if(waiting.empty()){
				try{
					w=new WorkerThread("additional worker",(Worker)workerClass.newInstance());
					w.start();
				}catch(InstantiationException e){
					e.printStackTrace();
				}catch(IllegalAccessException e){
					e.printStackTrace();
				}
			}else{
				w=(WorkerThread)waiting.pop();
			}
		}
		w.wake(data);

	}

}
