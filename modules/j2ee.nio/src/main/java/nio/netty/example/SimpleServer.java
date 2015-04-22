package nio.netty.example;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-21 下午3:41:01
 *   
 */
public class SimpleServer {
	private int port;
	public SimpleServer(int port){
		this.port=port;
	}
	public void start(final ChannelHandler... handlers){
		ChannelFactory factory=new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
		ServerBootstrap bootstrap=new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				return Channels.pipeline(handlers);
			}
		});

		bootstrap.setOption("child.tcpNoDelay",true);
		bootstrap.setOption("child.keepAlive",true);
		bootstrap.bind(new InetSocketAddress(port));
	}
}
