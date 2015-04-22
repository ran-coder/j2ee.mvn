package tutorial.thread.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PoolAdvanceServer { // 服务器端

	public static int clientCount = 0;
	static int port = 8888;
	ThreadPool threadPool = PoolManager.getInstance().createThreadPool(100,ServerWorker.class);//从线程管理器中取得线程池

	public PoolAdvanceServer(){
		
	}
	
	public static void main(String[] args) {    //启动服务器端主程序

		PoolAdvanceServer server = new PoolAdvanceServer();
		try {
			server.listen(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void listen(int port) throws IOException {

		ServerSocket server = new ServerSocket(port);
		System.out.println("The PoolAdvanceServer is listening......");
		while (true) { 
			clientCount++;
			Socket socket = server.accept();
			threadPool.performWork(socket);
		}
	}
}