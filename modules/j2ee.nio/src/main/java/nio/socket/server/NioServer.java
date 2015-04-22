package nio.socket.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.CharsetDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yuanwei
 * @version ctreateTime:2011-10-27 下午3:09:36
 */
public abstract class NioServer {
	protected Logger	log				=LoggerFactory.getLogger(getClass());
	protected int		port			=9090;
	protected Selector	selector;
	ByteBuffer			buffer;
	final static String	DEFAULT_CHARSET	="UTF-8";								// 默认码集
	CharsetDecoder		decoder;												// 解码器

	public abstract void init() throws IOException;

	public abstract void handleKey(SelectionKey key) throws IOException;

	public void start() {
		try{
			init();
		}catch(IOException e){
			e.printStackTrace();
			return;
		}
	}
}
