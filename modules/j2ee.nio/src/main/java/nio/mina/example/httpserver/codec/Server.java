package nio.mina.example.httpserver.codec;

import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * (<b>Entry point</b>) HTTP server
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 600461 $, $Date: 2007-12-03 18:55:52 +0900 (월, 03 12월 2007) $
 */
public class Server {
	/** Default HTTP port */
	private static final int	DEFAULT_PORT	=8080;

	/** Tile server revision number */
	public static final String	VERSION_STRING	="$Revision: 600461 $ $Date: 2007-12-03 18:55:52 +0900 (월, 03 12월 2007) $";

	public static void main(String[] args) {
		int port=DEFAULT_PORT;

		for(int i=0;i < args.length;i++){
			if(args[i].equals("-port")){
				port=Integer.parseInt(args[i + 1]);
			}
		}

		try{
			// Create an acceptor
			NioSocketAcceptor acceptor=new NioSocketAcceptor();

			// Create a service configuration
			acceptor.getFilterChain().addLast("protocolFilter",new ProtocolCodecFilter(new HttpServerProtocolCodecFactory()));
			acceptor.getFilterChain().addLast("logger",new LoggingFilter());
			acceptor.setHandler(new ServerHandler());
			acceptor.bind(new InetSocketAddress(port));

			System.out.println("Server now listening on port " + port);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}