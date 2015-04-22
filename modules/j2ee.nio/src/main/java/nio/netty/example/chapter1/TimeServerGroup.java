package nio.netty.example.chapter1;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import nio.netty.example.chapter1.handler.TimeServerHandler;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class TimeServerGroup {
	static final ChannelGroup	allChannels	=new DefaultChannelGroup("time-server");

	public void start(int port, final ChannelHandler... handlers) {
		ChannelFactory factory=new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
		ServerBootstrap bootstrap=new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				return Channels.pipeline(handlers);
			}
		});

		bootstrap.setOption("child.tcpNoDelay",true);
		bootstrap.setOption("child.keepAlive",true);
		Channel channel=bootstrap.bind(new InetSocketAddress(port));
		allChannels.add(channel);
		waitForShutdownCommand();
		ChannelGroupFuture future=allChannels.close();
		future.awaitUninterruptibly();
		factory.releaseExternalResources();
	}

	private void waitForShutdownCommand() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {
		new TimeServerGroup().start(8080,new TimeServerHandler());
	}
}