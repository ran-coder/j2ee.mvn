package nio.mina.example.httpserver;

import java.net.InetSocketAddress;

import nio.mina.example.httpserver.stream.HttpProtocolHandler;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.example.echoserver.ssl.BogusSslContextFactory;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * (<b>Entry point</b>) HTTP server
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 600461 $, $Date: 2007-12-03 18:55:52 +0900 (월, 03 12월 2007) $
 */
public class Main {
	/** Choose your favorite port number. */
	private static final int		PORT	=8080;

	private static final boolean	USE_SSL	=false;

	public static void main(String[] args) throws Exception {
		IoAcceptor acceptor=new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain=acceptor.getFilterChain();

		// Add SSL filter if SSL is enabled.
		if(USE_SSL){
			addSSLSupport(chain);
		}

		// Bind
		acceptor.setHandler(new HttpProtocolHandler());
		acceptor.bind(new InetSocketAddress(PORT));

		System.out.println("Listening on port " + PORT);
	}

	private static void addSSLSupport(DefaultIoFilterChainBuilder chain) throws Exception {
		System.out.println("SSL is enabled.");
		SslFilter sslFilter=new SslFilter(BogusSslContextFactory.getInstance(true));
		chain.addLast("sslFilter",sslFilter);
	}
}