package nio.socket.s1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

class AsyncServer implements Runnable {
	private ByteBuffer	readBuff	=ByteBuffer.allocate(1024);
	private ByteBuffer	writeBuff	=ByteBuffer.allocate(1024);
	private static int	port	=9090;

	public AsyncServer() {
		new Thread(this).start();
	}

	public void run() {
		try{
			// 生成一个侦听端
			ServerSocketChannel channel=ServerSocketChannel.open();
			// 将侦听端设为异步方式
			channel.configureBlocking(false);
			// 生成一个信号监视器
			Selector selector=Selector.open();
			// 侦听端绑定到一个端口
			channel.socket().bind(new InetSocketAddress(port));
			// 设置侦听端所选的异步信号OP_ACCEPT
			channel.register(selector,SelectionKey.OP_ACCEPT);
			System.out.println("echo server has been setup ......");

			while(true){
				if(selector.select() == 0){// 没有指定的I/O事件发生
					System.out.println("selector.select() == 0");
					continue;
				}
				Iterator<SelectionKey> it=selector.selectedKeys().iterator();
				while(it.hasNext()){
					SelectionKey key=it.next();
					System.out.println(key.readyOps());
					if(key.isAcceptable()){// 侦听端信号触发
						ServerSocketChannel server=(ServerSocketChannel)key.channel();
						// 接受一个新的连接
						SocketChannel sc=server.accept();
						sc.configureBlocking(false);
						// 设置该socket的异步信号OP_READ:当socket可读时，
						// 触发函数DealwithData();
						sc.register(selector,SelectionKey.OP_READ);
					}
					if(key.isReadable()){// 某socket可读信号
						dealwithData(key);
					}
					it.remove();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void dealwithData(SelectionKey key) throws IOException {
		//int count;
		// 由key获取指定socketchannel的引用
		SocketChannel sc=(SocketChannel)key.channel();
		readBuff.clear();
		// 读取数据到r_buff
		while((sc.read(readBuff)) > 0);
		// 确保r_buff可读
		readBuff.flip();

		writeBuff.clear();
		// 将r_buff内容拷入w_buff
		writeBuff.put(readBuff);
		writeBuff.flip();
		// 将数据返回给客户端
		echoToClient(sc);

		writeBuff.clear();
		readBuff.clear();
	}

	public void echoToClient(SocketChannel sc) throws IOException {
		while(writeBuff.hasRemaining())
			sc.write(writeBuff);
	}

	public static void main(String args[]) {
		if(args.length > 0){
			port=Integer.parseInt(args[0]);
		}
		new AsyncServer();
	}
}
