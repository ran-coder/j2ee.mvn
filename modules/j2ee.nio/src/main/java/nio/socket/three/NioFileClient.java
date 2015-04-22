package nio.socket.three;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liuyan
 */
public class NioFileClient {
	static int					SIZE		=2;
	final static int			bufferSize	=500 * 1024;
	static InetSocketAddress	ip			=new InetSocketAddress("localhost",12345);
	static CharsetEncoder		encoder		=Charset.forName("UTF-8").newEncoder();
	static String				target		="d:/webapp/103-thread.avi";

	static class Download implements Runnable {
		protected int	index;
		String			outfile	=null;

		public Download(int index) {
			this.index=index;
			this.outfile="d:/webapp/nio_" + index + ".avi";
		}

		public void run() {
			FileOutputStream fout=null;
			// FileChannel fcout = null;
			try{
				File file=new File(outfile);
				try{
					if(!file.exists() && file.createNewFile()){
						return;
					}
				}catch(IOException e){
					System.out.println("createNewFile:"+e);
				}
				fout=new FileOutputStream(file);
				// fcout = fout.getChannel();
			}catch(FileNotFoundException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try{
				long start=System.currentTimeMillis();
				// 打开客户端socket管道
				SocketChannel client=SocketChannel.open();
				// 客户端的管道的通讯模式
				client.configureBlocking(false);
				// 选择器
				Selector selector=Selector.open();
				// 往客户端管道上注册感兴趣的连接事件
				client.register(selector,SelectionKey.OP_CONNECT);
				// 配置IP
				client.connect(ip);
				// 配置缓存大小
				ByteBuffer buffer=ByteBuffer.allocate(bufferSize);
				int total=0;
				FOR: for(;;){
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
							channel.write(encoder.encode(CharBuffer.wrap(target)));
							// 之后为该客户端管道注册新的感兴趣的事件---读操作
							channel.register(selector,SelectionKey.OP_READ);
						}else if(key.isReadable()){
							// 由事件获得通讯管道
							SocketChannel channel=(SocketChannel)key.channel();
							// 从管道中读取数据放到缓存中
							int count=channel.read(buffer);
							System.out.println("Thread-"+index+"	down:" + count);
							if(count > 0){
								// 统计读取的字节数目
								total+=count;
								// 这样一来从posistion~limit这段缓存数据是有效可利用的
								// buffer.flip();
								buffer.clear();
								// 往输出文件中去写了
								if(count < bufferSize){
									byte[] overByte=new byte[count];
									for(int index=0;index < count;index++){
										overByte[index]=buffer.get(index);
									}
									fout.write(overByte);
								}else{
									fout.write(buffer.array());
								}
							}else{
								// 关闭客户端通道
								client.close();
								// 退出大循环
								break FOR;
							}
						}
					}
				}

				// 计算时间
				double last=(System.currentTimeMillis() - start) * 1.0 / 1000;
				System.out.println("Thread " + index + " downloaded " + total / 1024 + "kbytes in " + last + "s.");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// 启用线程池
		ExecutorService exec=Executors.newFixedThreadPool(SIZE);
		for(int index=1;index <= SIZE;index++){
			exec.execute(new Download(index));
		}
		exec.shutdown();

	}
}