package nio.mina.file.filter;

import nio.mina.file.filter.codec.FileDecoder;
import nio.mina.file.filter.codec.FileEncoder;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-16 下午3:05:46
 *   
 */
public class FileCodecFactory implements ProtocolCodecFactory{
	private final FileDecoder decoder;
	private final FileEncoder encoder;
	public FileCodecFactory(){
		decoder=new FileDecoder();
		encoder=new FileEncoder();
	}
	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

}
