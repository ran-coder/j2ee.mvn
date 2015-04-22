package nio.socket.three;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

/**
 * @author liuyan
 */
public class NioFileServer {
	static int	BLOCK	=500 * 1024;

	protected Selector			selector;
	protected String			filename		="d:/webapp/103-thread.avi";			// a big file
	protected ByteBuffer		clientBuffer	=ByteBuffer.allocate(BLOCK);
	protected CharsetDecoder	decoder;

	// 构造服务端口服务管道等等
	public NioFileServer(int port) throws IOException {
		selector=this.getSelector(port);
		decoder=Charset.forName("UTF-8").newDecoder();
	}

	// 获取Selector
	// 构造服务端口服务管道等等
	protected Selector getSelector(int port) throws IOException {
		ServerSocketChannel server=ServerSocketChannel.open();
		Selector sel=Selector.open();
		server.socket().bind(new InetSocketAddress(port));
		server.configureBlocking(false);

		// 刚开始就注册链接事件
		server.register(sel,SelectionKey.OP_ACCEPT);
		return sel;
	}

	// 服务启动的开始入口
	public void listen() {
		try{
			for(;;){
				selector.select();
				Iterator<SelectionKey> iter=selector.selectedKeys().iterator();
				while(iter.hasNext()){// 首先是最先感兴趣的连接事件
					SelectionKey key=iter.next();
					iter.remove();
					// 处理事件
					handleKey(key);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	// 处理事件
	protected void handleKey(SelectionKey key) throws IOException {
		if(key.isAcceptable()){ // 接收请求
			// 允许网络连接事件
			ServerSocketChannel server=(ServerSocketChannel)key.channel();
			SocketChannel channel=server.accept();
			channel.configureBlocking(false);
			// 网络管道准备处理读事件
			channel.register(selector,SelectionKey.OP_READ);
		}else if(key.isReadable()){ // 读信息
			SocketChannel channel=(SocketChannel)key.channel();
			// 从客户端读过来的数据块
			int count=channel.read(clientBuffer);
			if(count > 0){
				// 读取过来的缓存进行有效分割posistion设置为0保证从缓存的有效位置开始读取limit设置为原先的posistion上
				// 这样一来从posistion~limit这段缓存数据是有效可利用的
				clientBuffer.flip();
				// 对客户端缓存块进行编码
				CharBuffer charBuffer=decoder.decode(clientBuffer);
				System.out.println("Client >>download>>" + charBuffer.toString());
				// 对网络管道注册写事件
				SelectionKey wKey=channel.register(selector,SelectionKey.OP_WRITE);
				// 将网络管道附着上一个处理类HandleClient用于处理客户端事件的类
				try{
					wKey.attach(new HandleClient(charBuffer.toString()));
				}catch(Exception e){
					channel.close();
					System.out.println("HandleClient-Exception"+e);
				}
			}else{
				// 如客户端没有可读事件关闭管道
				channel.close();
			}
			clientBuffer.clear();
		}else if(key.isWritable()){ // 写事件
			SocketChannel channel=(SocketChannel)key.channel();
			// 从管道中将附着处理类对象HandleClient取出来
			HandleClient handle=(HandleClient)key.attachment();
			// 读取文件管道返回数据缓存
			ByteBuffer block=handle.readBlock();
			if(block != null){
				// System.out.println("---"+new String(block.array()));
				// 写给客户端
				channel.write(block);
			}else{
				handle.close();
				channel.close();
			}
		}
	}
	/**
	 * 处理客户端的内部类专门负责处理与用户的交互
	 */
	public class HandleClient {
		protected FileChannel	channel;
		protected ByteBuffer	buffer;
		String					filePath;

		/**
		 * 构造函数文件的管道初始化
		 * @param filePath
		 * @throws IOException
		 */
		public HandleClient(String filePath) throws IOException {
			File file=new File(filePath);
			if(!file.exists()){
				throw new IOException("file is not exists!");
			}
			// 文件的管道
			this.channel=new FileInputStream(filePath).getChannel();
			// 建立缓存
			this.buffer=ByteBuffer.allocate(BLOCK);
			this.filePath=filePath;
		}

		/**
		 * 读取文件管道中数据到缓存中
		 * @return
		 */
		public ByteBuffer readBlock() {
			buffer.clear();
			if(channel==null)return buffer;
			try{
				// 清除缓冲区的内容posistion设置为0limit设置为缓冲的容量大小capacity
				
				// 读取
				int count=channel.read(buffer);
				// 将缓存的中的posistion设置为0将缓存中的limit设置在原始posistion位置上
				buffer.flip();
				if(count <= 0) return null;
			}catch(IOException e){
				e.printStackTrace();
			}
			return buffer;
		}

		/**
		 * 关闭服务端的文件管道
		 */
		public void close() {
			try{
				channel.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		int port=12345;
		try{
			NioFileServer server=new NioFileServer(port);
			System.out.println("Listernint on " + port);
			while(true){
				server.listen();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}