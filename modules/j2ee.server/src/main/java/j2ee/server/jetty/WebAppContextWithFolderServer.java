package j2ee.server.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebAppContextWithFolderServer {
	public static void startWebAppContextWithFolderServer(Server server) throws Exception{
		WebAppContext context = new WebAppContext();
		context.setContextPath("/crm");
		context.setDescriptor("D:/Server/server/tomcat-6.0.29-x64/webapps/crm/WEB-INF/web.xml");
		context.setResourceBase("D:/Server/server/tomcat-6.0.29-x64/webapps/crm");
		context.setParentLoaderPriority(true);
		server.setHandler(context);

		server.start();
		server.join();
	}
	public static void main(String[] args) throws Exception {
		Server server = new Server(80);
		startWebAppContextWithFolderServer(server);
		
	}
}