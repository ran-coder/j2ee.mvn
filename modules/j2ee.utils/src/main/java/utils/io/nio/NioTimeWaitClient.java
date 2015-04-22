package utils.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author yuanwei
 * @version ctreateTime:2011-11-9 下午4:15:24
 */
public class NioTimeWaitClient {
	private SocketChannel		sc;
	private final static int	MAX_LENGTH	=1024;
	private ByteBuffer			buff		=ByteBuffer.allocate(MAX_LENGTH);
	private Selector			selector;
	private int					port;

	public NioTimeWaitClient() {
		this(20202);
	}

	public NioTimeWaitClient(int port) {
		System.out.println(0);
		this.port=port;
		connect();
		System.out.println(1);
	}

	void connect() {
		try{
			// 打开客户端socket管道
			SocketChannel client=SocketChannel.open();
			// 客户端的管道的通讯模式
			client.configureBlocking(false);
			// 选择器
			Selector selector=Selector.open();
			// 往客户端管道上注册感兴趣的连接事件
			client.register(selector,SelectionKey.OP_CONNECT);
			// 配置IP
			client.connect(new InetSocketAddress("127.0.0.1",port));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void send(long time) throws IOException {
		long start=System.currentTimeMillis();
		// 把回射消息放入w_buff中
		buff.clear();
		buff.putLong(time);
		buff.flip();
		try{
			sc.write(buff);
		}catch(IOException e){
		}
		buff.clear();
		// 间隔1秒
		try{
			sc.read(buff);
			buff.flip();
			System.out.println("reply content is: " + buff.get() + ",cost:" + (System.currentTimeMillis() - start));
		}catch(IOException e){
		}
		
		for(;;){
			// 阻塞返回发生感兴趣事件的数量
			selector.select();
			// 相当于获得感兴趣事件的集合迭代
			Iterator<SelectionKey> iter=selector.selectedKeys().iterator();
			while(iter.hasNext()){
				SelectionKey key=iter.next();
				//System.out.println("-----Thread " + index + "------------------" + key.readyOps());
				// 删除这个马上就要被处理的事件
				iter.remove();
				// 感兴趣的是可连接的事件
				if(key.isConnectable()){
					// 获得该事件中的管道对象
					SocketChannel channel=(SocketChannel)key.channel();
					// 如果该管道对象已经连接好了
					if(channel.isConnectionPending()) channel.finishConnect();
					// 往管道中写一些块信息
					buff.clear();
					buff.putLong(time);
					buff.flip();
					channel.write(buff);
					// 之后为该客户端管道注册新的感兴趣的事件---读操作
					channel.register(selector,SelectionKey.OP_READ);
				}else if(key.isReadable()){
					// 由事件获得通讯管道
					SocketChannel channel=(SocketChannel)key.channel();
					// 从管道中读取数据放到缓存中
					buff.clear();
					channel.read(buff);
					buff.flip();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		int port=21212;
		NioTimeWaitServer.start(port);
		NioTimeWaitClient client=new NioTimeWaitClient(port);
		for(int i=0;i < 20;i++){
			client.send(1000L);
		}
	}
}
