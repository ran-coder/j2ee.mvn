package utils.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.codec.ByteUtil;

/**
 * UDP程序测试<BR>
 * 时间服务器，采用线程池处理<BR>
 * 
 * @author LJZ
 * 
 */
public class UDPTimeServer extends Thread {
	final private static int	DAYTIME_PORT	=7897;

	private DatagramSocket		serverSocket	=null;

	private static final int	THREAD_POOL		=10;

	private ExecutorService		threadPool		=null;

	public UDPTimeServer() throws IOException {
		serverSocket=new DatagramSocket(DAYTIME_PORT);
		threadPool=Executors.newFixedThreadPool(THREAD_POOL);
		System.out.println("时间服务器启动成功,监听端口 [" + DAYTIME_PORT + "] 线程池 [" + THREAD_POOL + "].");
	}

	public void run() {
		while(true){
			try{
				byte buffer[]=new byte[256];
				DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
				serverSocket.receive(packet);
				System.out.println("收到客户端 [" + packet.getSocketAddress() + " ] 请求.");
				threadPool.execute(new HandlerThread(packet));
			}catch(IOException e){
				break;
			}
		}
	}

	/**
	 * 客户端数据处理线程
	 * 
	 * @author LJZ
	 * 
	 */
	private class HandlerThread extends Thread {
		private DatagramPacket	packet	=null;

		public HandlerThread(DatagramPacket p) {
			packet=p;
		}

		public void run() {
			try{
				String date=new Date().toString();
				byte[] buffer=date.getBytes();

				InetAddress address=packet.getAddress();
				int port=packet.getPort();
				packet=new DatagramPacket(buffer,buffer.length,address,port);
				serverSocket.send(packet);
				//ss
				System.out.println("线程 [" + getName() + "] 向客户端 [" + packet.getSocketAddress() + " ]  发送数据 ["
						+ ByteUtil.toHexString(buffer) + "].");
			}catch(Exception e){

			}finally{
				packet=null;
			}
		}
	}

	public static void main(String args[]) throws IOException {
		new UDPTimeServer().start();
	}
}
