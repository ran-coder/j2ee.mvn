package j2ee.server.jetty;

/**
 * @author yuanwei
 * @version ctreateTime:2011-7-25 下午3:40:42
 */
import java.net.ServerSocket;

import org.eclipse.jetty.rewrite.handler.RedirectPatternRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;

public abstract class JettyLaunch {

	public abstract String getContextPath();

	public int getServerPort() {
		return 8080;
	}

	public String getWarResourceBase() {
		return "src/main/webapp";
	}

	public String getHashLoginServiceConfig() {
		return "jetty-realm.properties";
	}

	public static void launch(JettyLaunch jettyLaunch) throws Exception {
		int port=jettyLaunch.getServerPort();
		// see if port is available
		try{
			ServerSocket s=new ServerSocket(port);
			s.close();
		}catch(Exception e){
			throw new RuntimeException("Port already in use",e);
		}

		Server server=new Server(port);

		RewriteHandler rewrite=new RewriteHandler();
		rewrite.setRewriteRequestURI(false);

		RedirectPatternRule redirect=new RedirectPatternRule();
		redirect.setPattern("/");
		redirect.setLocation(jettyLaunch.getContextPath());
		rewrite.addRule(redirect);

		WebAppContext webAppContext=new WebAppContext();
		webAppContext.setDescriptor(jettyLaunch.getWarResourceBase() + "/WEB-INF/web.xml");
		webAppContext.setContextPath(jettyLaunch.getContextPath());
		webAppContext.setParentLoaderPriority(true);
		webAppContext.getInitParams().put("org.eclipse.jetty.servlet.Default.useFileMappedBuffer","false");
		webAppContext.setResourceBase(jettyLaunch.getWarResourceBase());

		if(jettyLaunch.getHashLoginServiceConfig() != null){
			webAppContext.getSecurityHandler().setLoginService(new HashLoginService("default",jettyLaunch.getHashLoginServiceConfig()));
		}

		HandlerList handlers=new HandlerList();
		handlers.setHandlers(new Handler[]{ webAppContext, rewrite });
		server.setHandler(handlers);

		server.start();
		server.join();
	}
}
