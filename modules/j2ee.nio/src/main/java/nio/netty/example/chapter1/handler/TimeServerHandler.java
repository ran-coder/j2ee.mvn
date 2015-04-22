package nio.netty.example.chapter1.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-22 上午10:29:38
 *   
 */
public class TimeServerHandler extends SimpleChannelHandler {
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Channel ch=e.getChannel();
		ChannelBuffer time=ChannelBuffers.buffer(4);
		time.writeInt((int)(System.currentTimeMillis() / 1000));

		ChannelFuture future=ch.write(time);
		future.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) {
				Channel ch=future.getChannel();
				ch.close();
			}
		});
		//future.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}
}