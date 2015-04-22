package nio.netty.websocket;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * Generates the demo HTML page which is served at http://localhost:8080/
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 * @version $Rev: 2080 $, $Date: 2010-01-26 18:04:19 +0900 (Tue, 26 Jan 2010) $
 */
public class WebSocketServerIndexPage {
	
	static final String	WEBSOCKET_HTML	="D:/Server/IDE/eclipseWork/j2ee.mvn/modules/j2ee.nio/src/main/webapp/websocket.html";
	public static ChannelBuffer getContent(String webSocketLocation) throws Exception {
		return ChannelBuffers.copiedBuffer(FileUtils.readFileToByteArray(new File(WEBSOCKET_HTML)));
	}
}
