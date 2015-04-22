package j2ee.server.jetty.spdy;

import j2ee.server.jetty.SystemOutLog;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.spdy.http.HTTPSPDYServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 * <Configure id="Server" class="org.eclipse.jetty.server.Server">
 *   <New id="sslContextFactory" class="org.eclipse.jetty.util.ssl.SslContextFactory">
 *      <Set name="keyStorePath">src/main/resources/spdy.keystore</Set>
 *      <Set name="keyStorePassword">secret</Set>
 *      <Set name="protocol">TLSv1</Set>
 *   </New>
 *   <Call name="addConnector">
 *      <Arg>
 *           <New class="org.eclipse.jetty.spdy.http.HTTPSPDYServerConnector">
                <Arg>
                    <Ref id="sslContextFactory" />
                </Arg>
                <Set name="Port">8443</Set>
            </New>
        </Arg>
    </Call>
 
   // Use standard XML configuration for the other handlers and other
  // stuff you want to add
 
</Configure>
 *
 */
public class SPDYServerLauncher {
	
	public static void main(String[] args) throws Exception {
		
		// the server to start
		Server server=new Server();
		
		// the ssl context to use
		SslContextFactory sslFactory=new SslContextFactory();
		//keytool -genkey -keystore spdy.keystore -keyalg RSA
		sslFactory.setKeyStorePath("src/main/resources/jetty/spdy.keystore");
		sslFactory.setKeyStorePassword("123456");
		sslFactory.setProtocol("TLSv1");
		
		// simple connector to add to serve content using spdy
		Connector connector=new HTTPSPDYServerConnector(sslFactory);
		connector.setPort(8443);
		
		// add connector to the server
		server.addConnector(connector);
		
		// add a handler to serve content
		ContextHandler contextHandler=new ContextHandler();
		contextHandler.setContextPath("/");
		contextHandler.setResourceBase("src/main/webapp");
		contextHandler.setHandler(new ResourceHandler());

		server.setHandler(contextHandler);

		HandlerCollection handlers = new HandlerCollection();
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		RequestLogHandler requestLogHandler = new RequestLogHandler();
		requestLogHandler.setRequestLog(new SystemOutLog());
		handlers.setHandlers(new Handler[]{contexts,new DefaultHandler(),requestLogHandler});
		server.setHandler(handlers);

		server.start();
		server.join();
	}
}