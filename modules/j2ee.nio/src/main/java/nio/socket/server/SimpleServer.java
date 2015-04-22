package nio.socket.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-10-27 下午3:12:17
 *   
 */
public class SimpleServer extends NioServer {

	@Override
	public void init() throws IOException {
		decoder=Charset.forName(DEFAULT_CHARSET).newDecoder();
		
		buffer=ByteBuffer.allocate(1024);
		selector=Selector.open();// 生成一个信号监视器
		ServerSocketChannel ssc=ServerSocketChannel.open();
		ssc.configureBlocking(false);// 将侦听端设为异步方式
		ssc.socket().bind(new InetSocketAddress(port),10);// 侦听端绑定到一个端口
		ssc.register(selector,SelectionKey.OP_ACCEPT);//设置侦听端所选的异步信号OP_ACCEPT,attachment=null
		//ssc.register(selector,SelectionKey.OP_ACCEPT,ByteBuffer.allocate(1024));//attachment限制位2014byte
		System.out.println("Server is running! ");
		try{
			while(true){
				// 与通常的程序不同，这里使用channel.accpet()接受客户端连接请求，而不是在socket对象上调用accept(),
				//这里在调用accept()方法时如果通道配置为非阻塞模式,那么accept()方法立即返回null，并不阻塞
				//阻塞最多10秒钟等待可用的客户端连接
				selector.select(10);
				Iterator<SelectionKey> it=this.selector.selectedKeys().iterator();
				while(it.hasNext()){
					handleKey(it.next());
					it.remove();
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void handleKey(SelectionKey key) throws IOException{
		if(key.isAcceptable()){
			ServerSocketChannel connect=(ServerSocketChannel)key.channel();
			SocketChannel sc=connect.accept();
			sc.configureBlocking(false);
			sc.register(selector,SelectionKey.OP_READ);
			log.info(sc.socket().toString());
		}
		if(key.isReadable()){
			SocketChannel client=(SocketChannel)key.channel();
			buffer.clear();
			if(client.read(buffer)!=0){
				buffer.flip();
				log.info("client>>{}",decoder.decode(buffer));
				client.write(buffer);
			}else {
				client.close();
			}
			////异常时 将本socket的事件在选择器中删除key.cancel(); 
		}
		if(key.isWritable()){
			
		}
	}

	public static void main(String[] args) {
		new SimpleServer().start();
	}
}
