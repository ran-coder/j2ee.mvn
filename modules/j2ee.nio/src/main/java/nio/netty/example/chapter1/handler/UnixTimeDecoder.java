package nio.netty.example.chapter1.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-22 上午10:34:11
 *   
 */
public class UnixTimeDecoder extends FrameDecoder {
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
		if(buffer.readableBytes() < 4){
			System.out.println("TimeDecoder:buffer.readableBytes() < 4");
			return null;
		}
		return new UnixTime(buffer.readInt());
	}
}