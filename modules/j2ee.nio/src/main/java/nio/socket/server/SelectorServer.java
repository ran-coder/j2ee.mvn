package nio.socket.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

public class SelectorServer {
	final static int					DEFAULT_SERVERPORT	=9527;			// 默认端口
	final static int					DEFAULT_BUFFERSIZE	=1024;			// 默认缓冲区大小为1024字节
	final static String				DEFAULT_CHARSET		="UTF-8";		// 默认码集
	final static String				DEFAULT_FILENAME	="bigfile.dat";
	private ServerSocketChannel			channel;
	//private LinkedList<SocketChannel>	clients;
	private Selector					selector;							// 选择器
	private ByteBuffer					buffer;							// 字节缓冲区
	private int							port;
	private Charset						charset;							// 字符集
	private CharsetDecoder				decoder;							// 解码器

	public SelectorServer(int port) throws IOException {
		this.port=port;
		//this.clients=new LinkedList<SocketChannel>();
		this.channel=null;
		this.selector=Selector.open();// 打开选择器
		this.buffer=ByteBuffer.allocate(DEFAULT_BUFFERSIZE);
		this.charset=Charset.forName(DEFAULT_CHARSET);
		this.decoder=this.charset.newDecoder();

	}

	protected void handleKey(SelectionKey key) throws IOException {// 处理事件
		if(key.isAcceptable()){ // 接收请求
			ServerSocketChannel server=(ServerSocketChannel)key.channel();// 取出对应的服务器通道
			SocketChannel channel=server.accept();
			channel.configureBlocking(false);
			channel.register(selector,SelectionKey.OP_READ);// 客户socket通道注册读操作
			System.out.println(channel.socket().toString());
		}
		if(key.isReadable()){ // 读信息
			SocketChannel channel=(SocketChannel)key.channel();
			int count=channel.read(this.buffer);
			if(count > 0){
				this.buffer.flip();
				CharBuffer charBuffer=decoder.decode(this.buffer);
				String recive=charBuffer==null?null:charBuffer.toString();
				System.out.println("Client >>" + recive);
				///SelectionKey wKey=channel.register(selector,SelectionKey.OP_WRITE);// 为客户sockt通道注册写操作
				///wKey.attach(new HandleClient());
				if("close".equals(recive)){
					channel.socket().close();
				}else if(recive!=null){
					buffer.clear();
					buffer.put(ByteBuffer.wrap(recive.getBytes()));
					buffer.flip();
					channel.write(buffer);
				}
			}else{// 客户已经断开
				channel.close();
			}
			this.buffer.clear();// 清空缓冲区
		}
		/*if(key.isWritable()){ // 写事件
			SocketChannel channel=(SocketChannel)key.channel();
			HandleClient handle=(HandleClient)key.attachment();// 取出处理者
			ByteBuffer block=ByteBuffer.wrap(handle.readBlock().getBytes());
			channel.write(block);
			// channel.socket().getInputStream().(block);
			// PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
			// channel.socket().getOutputStream())), true);
			// out.write(block.toString());

		}*/

	}

	public void listen() throws IOException { // 服务器开始监听端口，提供服务
		ServerSocket socket;
		channel=ServerSocketChannel.open(); // 打开通道
		socket=channel.socket(); // 得到与通到相关的socket对象
		socket.bind(new InetSocketAddress(port),10); // 将scoket榜定在制定的端口上
		// 配置通到使用非阻塞模式，在非阻塞模式下，可以编写多道程序同时避免使用复杂的多线程
		channel.configureBlocking(false);
		channel.register(selector,SelectionKey.OP_ACCEPT);
		try{
			while(true){
				// 与通常的程序不同，这里使用channel.accpet()接受客户端连接请求，而不是在socket对象上调用accept(),
				//这里在调用accept()方法时如果通道配置为非阻塞模式,那么accept()方法立即返回null，并不阻塞
				this.selector.select();
				Iterator<SelectionKey> it=this.selector.selectedKeys().iterator();
				while(it.hasNext()){
					SelectionKey key=it.next();
					this.handleKey(key);
					it.remove();
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	class HandleClient {
		private String	strGreeting	="welcome to SelectorServer";

		public HandleClient() throws IOException {}

		public String readBlock() {// 读块数据
			return this.strGreeting;
		}

		public void close() {

		}
	}
	public static void main(String[] args) throws IOException {
		System.out.println("服务器启动");
		SelectorServer server=new SelectorServer(SelectorServer.DEFAULT_SERVERPORT);
		server.listen(); // 服务器开始监听端口，提供服务
	}

}
