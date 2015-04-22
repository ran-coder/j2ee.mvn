package nio.mina.file.simple.client;

import java.net.InetSocketAddress;

import nio.mina.file.Command;
import nio.mina.file.Constants;
import nio.mina.file.simple.ClientConfig;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-16 下午3:21:53
 *   
 */
public class SimpleFileClient {
	private NioSocketConnector connector;
	private ConnectFuture cf;
	private ClientConfig clientConfig;
	public SimpleFileClient(String host,int port,String input,String output){
		this.clientConfig=new ClientConfig(host,port,input,output);
	}

	public SimpleFileClient(ClientConfig clientConfig){
		this.clientConfig=clientConfig;
	}
	public void run() {
		connect();
	}
	public void connect(){
		// 创建客户端连接器.
		connector=new NioSocketConnector();
		//connector.getFilterChain().addLast("logger",new LoggingFilter());
		connector.setConnectTimeoutMillis(30*1000);
		connector.setHandler(new SimpleFileClientHandler(clientConfig));// 设置事件处理器
		LoggingFilter logFilter=new LoggingFilter();
		logFilter.setSessionOpenedLogLevel(LogLevel.DEBUG);
		logFilter.setSessionIdleLogLevel(LogLevel.NONE);
		logFilter.setMessageReceivedLogLevel(LogLevel.NONE);
		logFilter.setMessageSentLogLevel(LogLevel.NONE);
		connector.getFilterChain().addLast("logger",logFilter);
		connector.getFilterChain().addLast("serial",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

		cf=connector.connect(new InetSocketAddress(clientConfig.getHost(),clientConfig.getPort()));// 建立连接
		cf.awaitUninterruptibly();// 等待连接创建完成
		//System.out.format("%2$d%1$.4f\n",(System.nanoTime()-start)/1.0e6,count);
		if(!cf.isConnected()){
			System.out.println("connect is closed!");
			return;
		}
		cf.getSession().write(new Command(Constants.Cmd.START,clientConfig.getInput()));

		//cf.getSession().write("quit");// 发送消息
		//cf.getSession().getCloseFuture().awaitUninterruptibly();// 等待连接断开
		//connector.dispose();
	}


	public static void main(String[] args) {
		SimpleFileClient client=new SimpleFileClient("127.0.0.1",9090,"C:/java/nio/1.png","C:/java/nio/1-0.png");
		client.run();
	}
}
