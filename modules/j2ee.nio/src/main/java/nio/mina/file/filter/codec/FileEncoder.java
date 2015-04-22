package nio.mina.file.filter.codec;

import java.nio.charset.Charset;

import nio.mina.file.Command;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-16 下午2:25:45
 *   
 */
public class FileEncoder implements ProtocolEncoder{
	private final Charset charset=Charset.forName("UTF-8");
	
	/**
	 * 第一个字节:命令标示符 byte command		1
	 * long position +8 						2  9
	 * long size     +8							10 17
	 * long length   +8							18 25
	 * String path   +4+n						26 29 n		4个字节表示int长度+本身长度
	 * byte[] bytes 直接写入
	 */
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if(message!=null && message instanceof Command){
			Command cmd=(Command)message;
			IoBuffer buff=IoBuffer.allocate(1024).setAutoExpand(true);
			buff.put(cmd.getCommand());
			buff.putLong(cmd.getPosition());
			buff.putLong(cmd.getSize());
			buff.putLong(cmd.getLength());
			if(cmd.getPath()==null||cmd.getPath().trim().length()<1){
				buff.putInt(0);
			}else{
				cmd.setPath(cmd.getPath().trim());
				buff.putInt(cmd.getPath().length());
				buff.put(cmd.getPath().getBytes(charset));
			}
			buff.put(cmd.getBytes());
			out.write(buff);
		}else{
			session.close(true);
		}
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		
	}

}
