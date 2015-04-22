package j2ee.server.jetty.example;

import java.io.File;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {
	private static final int	PORT_NUMBER	=8080;

	public static void main(String[] args) {
		new JettyServer().run();
	}

	public void run() {
		try{
			configureLog4J();

			Server jetty=new Server(PORT_NUMBER);

			String appDir=getWebAppDirectory("source/webapp/demo").getAbsolutePath();
			//jetty.addWebApplication("/demo",appDir);
			jetty.setHandler(new WebAppContext("/demo",appDir));

			//SocketListener httpPort=new SocketListener();
			//httpPort.setPort(PORT_NUMBER);
			//jetty.addListener(httpPort);

			jetty.start();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	private void configureLog4J() {
		ConsoleAppender console=new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN));
		Logger root=Logger.getRootLogger();
		root.setLevel(Level.INFO);
		root.addAppender(console);
	}

	private File getWebAppDirectory(String relativeDir) {
		File directory=getDirectory(relativeDir);
		File webxml=new File(directory,"WEB-INF/web.xml");
		if(!webxml.exists()){ throw new RuntimeException("Cannot find web.xml at " + webxml); }
		return directory;
	}

	private File getDirectory(String relativeDir) {
		File directory=getFile(relativeDir);
		if(!directory.isDirectory()){ throw new RuntimeException("Not a directory: " + directory); }
		return directory;
	}

	private File getFile(String relativeName) {
		File file=new File(relativeName);
		if(!file.exists()){ throw new RuntimeException("File not found: " + file); }
		return file;
	}
}