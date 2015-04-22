package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yuanwei
 * @version ctreateTime:2011-10-27 上午9:13:42
 */
public class SocketExample {
	static void execute() throws IOException {
		int[] ports={9090};
		ByteBuffer	echoBuffer	=ByteBuffer.allocate(1024);
		// 创建一个新的选择器
		Selector selector=Selector.open();

		// 打开在每个端口上的监听，并向给定的选择器注册此通道接受客户端连接的I/O事件。
		for(int i=0;i < ports.length;i++){
			// 打开服务器套接字通道
			ServerSocketChannel ssc=ServerSocketChannel.open();
			// 设置此通道为非阻塞模式
			ssc.configureBlocking(false);
			// 绑定到特定地址
			ServerSocket ss=ssc.socket();
			InetSocketAddress address=new InetSocketAddress(ports[i]);
			ss.bind(address);
			// 向给定的选择器注册此通道的接受连接事件
			ssc.register(selector,SelectionKey.OP_ACCEPT);
			System.out.println("Going to listen on " + ports[i]);
		}

		while(true){
			// 这个方法会阻塞，直到至少有一个已注册的事件发生。
			// 当一个或者更多的事件发生时，此方法将返回所发生的事件的数量。
			selector.select();

			// 迭代所有的选择键，以处理特定的I/O事件。
			Set<SelectionKey> selectionKeys=selector.selectedKeys();
			Iterator<SelectionKey> iter=selectionKeys.iterator();

			SocketChannel sc;
			while(iter.hasNext()){
				SelectionKey key=iter.next();

				if((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
					// 接受服务器套接字撒很能够传入的新的连接，并处理接受连接事件。
					ServerSocketChannel ssc=(ServerSocketChannel)key.channel();
					sc=ssc.accept();
					// 将新连接的套接字通道设置为非阻塞模式
					sc.configureBlocking(false);

					// 接受连接后，在此通道上从新注册读取事件，以便接收数据。
					sc.register(selector,SelectionKey.OP_READ);
					// 删除处理过的选择键
					iter.remove();

					System.out.println("Got connection from " + sc);
				}else if((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
					// 处理读取事件，读取套接字通道中发来的数据。
					sc=(SocketChannel)key.channel();

					// 读取数据
					int bytesEchoed=0;
					while(true){
						echoBuffer.clear();
						int r=sc.read(echoBuffer);

						if(r == -1){
							break;
						}

						echoBuffer.flip();
						sc.write(echoBuffer);

						bytesEchoed+=r;
					}
					System.out.println("Echoed " + bytesEchoed + " from " + sc);
					// 删除处理过的选择键
					iter.remove();
				}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
