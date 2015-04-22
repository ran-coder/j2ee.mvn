package j2ee.tutorial.thread.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerWorker implements Worker {

	public void run(Object data) {
		//处理客户端请求方法
		processRequest((Socket)data);
	}

	private void processRequest(Socket socket) {
		try {
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			DataInputStream dis = new DataInputStream(is);
			DataOutputStream dos = new DataOutputStream(os);
			int receiveInt = dis.readInt(); // 读取客户端请求
			System.out.println("The message from client"+PoolAdvanceServer.clientCount+" is:" + receiveInt);
			dos.writeInt(receiveInt * 18); // 返回client端
			dos.flush(); // 强制清空缓冲区
			dos.close();
			dis.close();
			is.close();
			
			delay(10l);
//			线程处理完毕后，减少client端的数量
			PoolAdvanceServer.clientCount--;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void delay(long l) {

		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
