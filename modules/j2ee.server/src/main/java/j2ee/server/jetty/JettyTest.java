package j2ee.server.jetty;

import j2ee.servlet.SimpleServelt;
import j2ee.servlet.ThreadLocalServelt;

import java.io.FileInputStream;

import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

import utils.io.PathUtil;
import utils.net.HttpUtil;

public class JettyTest {
	public static void simpleTest(Server server) {
		server.setHandler(new DefaultHandler());
		try{
			server.start();
			System.out.println("Jetty Started ...");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void testServlet(Server server) throws Exception {
		ServletContextHandler servletContextHandler=new ServletContextHandler(server,"/",true,false);
		servletContextHandler.addServlet(SimpleServelt.class,"/simple");
		server.start();
		System.out.println("Jetty7 Started ...");
	}
	public static void testChartServlet(Server server) throws Exception {
		ServletContextHandler servletContextHandler=new ServletContextHandler(server,"/",true,false);
		servletContextHandler.addServlet(j2ee.servlet.ChartServlet.class,"/chart");
		server.start();
		System.out.println("Jetty7 Started ...");
	}

	public static void testThreadLocalServelt(Server server) throws Exception {
		ServletContextHandler servletContextHandler=new ServletContextHandler(server,"/",true,false);
		servletContextHandler.addServlet(ThreadLocalServelt.class,"/simple");
		server.start();
		System.out.println("Jetty7 Started ...");

		Runnable run=new Runnable() {
			public void run() {
				try{
					//UrlUtil.getHttpExcp("http://127.0.0.1:7788/simple");
					HttpUtil.sendPostByHttpClient("http://127.0.0.1:7788/simple",null);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		for(int i=0;i < 10;i++){
			new Thread(run).start();
		}
	}

	public static void testWebAppContext(Server server) throws Exception {
		//server.addListener(listener);// 将监听类注册到server中
		//HttpContext context=new HttpContext(); // 创建一个新HttpContext
		//context.setContextPath("/app/*"); // 设置访问路径
		//context.setResourceBase("c:/root/"); // 设置静态资源路径
		//context.addHandler(new ResourceHandler()); // 为这个HttpContext添加一个静态资源处理器
		//server.addContext(context); // 将这个HttpContext注册到server中
		WebAppContext context=new WebAppContext();
		context.setDescriptor("WebRoot/WEB-INF/web.xml");
		context.setResourceBase("WebRoot/");
		context.setContextPath("/");
		context.setParentLoaderPriority(true);

		server.setHandler(context);

		server.start();
		server.join();
	}
	public static void testWebAppContext1(Server server) throws Exception {
		String jetty_home = "jetty";

		WebAppContext webapp = new WebAppContext(jetty_home + "/web", "/jetty");
		webapp.setDefaultsDescriptor(jetty_home+"/etc/webdefault.xml");
		server.setHandler(webapp);
		server.start();
	}
	public static void testSSL(Server server) throws Exception {
		SslSocketConnector sslConnector=new SslSocketConnector();
		sslConnector.setPort(7777);
		//sslConnector.setKeystore(null);
		sslConnector.setHost("ran");
		((AbstractConnector)sslConnector).setThreadPool(new QueuedThreadPool(20));
		server.addConnector(sslConnector);
		ServletContextHandler context=new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		HandlerList handlers=new HandlerList();
		handlers.setHandlers(new Handler[]{ context, new DefaultHandler() });
		server.setHandler(handlers);

		context.addServlet(SimpleServelt.class,"/*");

		server.start();
		server.join();
		System.out.println("Jetty7 SSL Started ...");
	}

	public static void startWithJettyXml(Server server) throws Exception{
		XmlConfiguration cfg=null;
		//cfg=new XmlConfiguration(new FileInputStream("./conf/jetty.xml"));
		String path=PathUtil.getFullPathRelateClass("./conf/jetty.xml",JettyTest.class);
		cfg=new XmlConfiguration(new FileInputStream(path));
		cfg.configure(server);
		server.start();
		System.out.println("Jetty Started ...");
	}
	public static void startProxy() throws Exception{
		Server server=new Server();
		XmlConfiguration cfg=null;
		//cfg=new XmlConfiguration(new FileInputStream("./conf/jetty.xml"));
		String path=PathUtil.getFullPathRelateClass("./conf/jetty-proxy.xml",JettyTest.class);
		cfg=new XmlConfiguration(new FileInputStream(path));
		cfg.configure(server);
		server.start();
		System.out.println("Jetty Started ...");
	}
	public static void main(String[] args) throws Exception {
		Server server=new Server(7788);//port覆盖jetty.xml中port
		//startWithJettyXml(server);
		testChartServlet(server);

		//startProxy();
		// simpleTest(server);
		// testServlet(server);
		//testThreadLocalServelt(server);
		// testSSL(server);
		// server.stop();
	}

}
