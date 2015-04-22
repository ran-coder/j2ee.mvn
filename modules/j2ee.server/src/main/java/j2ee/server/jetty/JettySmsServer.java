package j2ee.server.jetty;

import j2ee.servlet.SmsServelt;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class JettySmsServer {
	public static void smsServer(Server server) throws Exception {
		ServletContextHandler servletContextHandler=new ServletContextHandler(server,"/",true,false);
		servletContextHandler.addServlet(SmsServelt.class,"/sms");
		server.start();
		System.out.println("Jetty7 Started ...");
	}
	public static void main(String[] args) throws Exception {
		Server server=new Server(7788);//port覆盖jetty.xml中port
		smsServer(server);
	}

}
