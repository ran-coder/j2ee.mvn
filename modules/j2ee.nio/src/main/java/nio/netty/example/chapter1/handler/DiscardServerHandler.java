package nio.netty.example.chapter1.handler;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-22 上午10:29:13
 *   
 */
public class DiscardServerHandler extends SimpleChannelHandler {
	protected Logger	log	=LoggerFactory.getLogger(DiscardServerHandler.class);

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		ChannelBuffer buff=(ChannelBuffer)e.getMessage();
		System.out.println(buff.toString(0,Math.min(8,buff.readableBytes()),Charset.defaultCharset()));
		e.getChannel().write(e.getMessage());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		Channel ch=e.getChannel();
		ch.close();
	}
}