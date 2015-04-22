package j2ee.server.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebAppContextWithWarServer {
	public static void main(String[] args) throws Exception {
		Server server = new Server(80);

		WebAppContext context = new WebAppContext();
		context.setContextPath("/myapp");
		context.setWar("E:/share/test/struts2-blank.war");
		server.setHandler(context);

		server.start();
		server.join();
	}
}