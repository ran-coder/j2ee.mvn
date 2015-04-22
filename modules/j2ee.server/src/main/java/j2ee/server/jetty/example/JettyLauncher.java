package j2ee.server.jetty.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Launch Jetty embedded.
 */
public class JettyLauncher {
	/**
	 * Sets up and runs server.
	 * @param args The command line arguments
	 * @throws Exception Don't care because top level
	 */
	public static void main(String[] args) throws Exception {
		Server server=new Server();

		SelectChannelConnector connector=new SelectChannelConnector();
		connector.setPort(8080);
		server.addConnector(connector);
		server.setStopAtShutdown(true);

		server.setHandler(new WebAppContext("web","/dwr"));

		server.start();
		server.join();
	}
}