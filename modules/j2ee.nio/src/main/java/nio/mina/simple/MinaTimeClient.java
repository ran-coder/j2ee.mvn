package nio.mina.simple;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.concurrent.NamingExecutors;

public class MinaTimeClient {
	static AtomicInteger	count	=new AtomicInteger();
	static Logger log=LoggerFactory.getLogger(MinaTimeClient.class);
	static class TimeClientHandler extends BaseIoHandlerAdapter{
		@Override
		public void sessionCreated(IoSession session) throws Exception {
		}
		@Override  
		public void messageReceived(IoSession session, Object message) throws Exception {  
			//log.info("message:{}",message);
		}
		@Override
		public void messageSent(IoSession session, Object message) throws Exception {
			session.setAttribute("JSESSION","JSESSION");
		}
	}
	static class ConnectThread implements Runnable{
		@Override
		public void run() {
			int i=0;
			//long start;
			while(i++<500){
				//start=System.nanoTime();
				connect(count.incrementAndGet());
				//log.info("cost:{}",(System.nanoTime()-start)/1.0e6);
				//System.out.format("%1$.4f\n",(System.nanoTime()-start)/1.0e6);
				//try{TimeUnit.SECONDS.sleep(5);}catch(InterruptedException e){}
			}
		}
		public void connect(int count){
			// 创建客户端连接器.
			NioSocketConnector connector=new NioSocketConnector();
			//connector.getFilterChain().addLast("logger",new LoggingFilter());
			connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
			connector.setConnectTimeoutMillis(30*1000);
			connector.setHandler(new TimeClientHandler());// 设置事件处理器

			long start=System.nanoTime();
			ConnectFuture cf=connector.connect(new InetSocketAddress("127.0.0.1",9090));// 建立连接
			cf.awaitUninterruptibly();// 等待连接创建完成
			System.out.format("%2$d%1$.4f\n",(System.nanoTime()-start)/1.0e6,count);
			if(!cf.isConnected()){
				System.out.println("connect is closed!");
				return;
			}
			cf.getSession().write(count);
			cf.getSession().write("quit");// 发送消息
			cf.getSession().getCloseFuture().awaitUninterruptibly();// 等待连接断开
			connector.dispose();
			
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executor=NamingExecutors.newFixedCachedThreadPool("connect",10);
		NamingExecutors.execute(executor,new ConnectThread(),500);
		executor.shutdown();
	}
}
