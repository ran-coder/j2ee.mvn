package nio.mina.example.proxy;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.example.proxy.ClientToProxyIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


public class ProxyMain {
	static final int serverPort=6060;
	static final String target="127.0.0.1";
	static final int targetPort=3128;

	public static void main(String[] args) throws Exception {
		// Create TCP/IP acceptor.
		NioSocketAcceptor acceptor=new NioSocketAcceptor();
		// Create TCP/IP connector.
		IoConnector connector=new NioSocketConnector();
		// Set connect timeout.
		connector.setConnectTimeoutMillis(30 * 1000L);
		ClientToProxyIoHandler handler=new ClientToProxyIoHandler(connector,new InetSocketAddress(target,targetPort));

		// Start proxy.
		acceptor.setHandler(handler);
		acceptor.bind(new InetSocketAddress(serverPort));

	}
}
