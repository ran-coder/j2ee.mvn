package utils.net;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * * 非阻塞式TCP时间服务器 * *
 * 
 * @author LJZ *
 */
public class NIOTCPTimeServer extends Thread {
	private int					listenPort		= 8485;
	private ServerSocketChannel	serverSocket	= null;
	private Selector			acceptSelector	= null;
	private Selector			readSelector	= null;

	public NIOTCPTimeServer(int port) {
		this.listenPort=port;
	}

	/** * 主线程方法只处理客户端连接<BR> * 同时将连接客户端事件响应注册为读响应<BR> */
	public void run() {
		try {
			acceptSelector = Selector.open();
			readSelector = Selector.open();
			serverSocket = ServerSocketChannel.open();
			serverSocket.configureBlocking(false);
			serverSocket.socket().bind(new InetSocketAddress(listenPort));
			serverSocket.register(acceptSelector, SelectionKey.OP_ACCEPT);
			System.out.println("服务器启动成功, 监听端口 [" + listenPort + "]");
			new ReadThread().start();
		} catch (Exception e) {
			System.out.println("服务器启动失败!");
			e.printStackTrace();
		}
		while (true) {
			try {
				if (acceptSelector.select(100) > 0) {
					Set<SelectionKey> keys = acceptSelector.selectedKeys();
					for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
						SelectionKey key = (SelectionKey) i.next();
						i.remove();
						ServerSocketChannel readyChannel = (ServerSocketChannel) key.channel();
						SocketChannel incomingChannel = readyChannel.accept();
						System.out.println("客户端 [" + incomingChannel.socket().getRemoteSocketAddress() + "] 与服务器建立连接.");
						incomingChannel.configureBlocking(false);
						incomingChannel.register(readSelector, SelectionKey.OP_READ, new StringBuffer());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * * 客户端数据读线程 * *
	 * 
	 * @author LJZ *
	 */
	private class ReadThread extends Thread {
		public void run() {
			int keysReady = -1;
			while (true) {
				try {
					/** * 检查客户数据事件，超时1秒 */
					keysReady = readSelector.select(100);
					if (keysReady > 0) {
						Set<SelectionKey> readyKeys = readSelector.selectedKeys();
						for (Iterator<SelectionKey> i = readyKeys.iterator(); i.hasNext();) {
							SelectionKey key = (SelectionKey) i.next();
							SocketChannel incomingChannel = (SocketChannel) key.channel();
							readData(incomingChannel);
							i.remove();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void readData(SocketChannel channel) {
			try {
				ByteBuffer readBuf = ByteBuffer.allocate(1024);
				if (channel.read(readBuf) > 0) {
					readBuf.flip();
					//ss
					System.out.println("收到客户端 [" + channel.socket().getRemoteSocketAddress() + "] 数据 ["
							+ readBuf + "].");
				}
			} catch (Exception e) {
				try {
					channel.close();
					System.out.println("关闭一个终端.");
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * *
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new NIOTCPTimeServer(8485).start();
		} catch (Exception e) {
		}
	}
}
