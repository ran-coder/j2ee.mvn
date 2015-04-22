package nio.netty.example.chapter1.handler;

import java.util.Date;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import utils.DateUtil;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-22 上午10:34:05
 *   
 */
public class TimeClientHandler extends SimpleChannelHandler {
	private final ChannelBuffer	buf	=ChannelBuffers.dynamicBuffer();

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		/** ver1 */
		System.out.println("************ ver1 ************");
		buf.writeBytes((ChannelBuffer)e.getMessage());
		System.out.println(DateUtil.toString(new Date(buf.readInt() * 1000L)));

		/** ver2 */
		System.out.println("************ ver2 ************");
		buf.clear();
		buf.writeBytes((ChannelBuffer)e.getMessage());
		if(buf.readableBytes() >= 4){
			System.out.println(new Date(buf.readInt() * 1000L));
		}

		e.getChannel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}
}