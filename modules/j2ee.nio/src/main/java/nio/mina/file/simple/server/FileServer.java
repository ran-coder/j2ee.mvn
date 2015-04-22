package nio.mina.file.simple.server;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import nio.mina.filter.DefaultProfilerTimerFilter;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.integration.jmx.IoServiceMBean;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author yuanwei
 * @version ctreateTime:2011-11-14 下午4:17:07
 */
public class FileServer {
	static Logger				log		=LoggerFactory.getLogger(FileServer.class);
	private static final int	PORT	=9090;										// 定义监听端口
	static final String	CHARSET="UTF-8";

	public static void main(String[] args) throws IOException {
		NioSocketAcceptor acceptor=new NioSocketAcceptor();
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);

		MBeanServer mBeanServer=ManagementFactory.getPlatformMBeanServer();
		IoServiceMBean acceptorMBean=new IoServiceMBean(acceptor);
		ObjectName acceptorName=null;
		try{
			acceptorName=new ObjectName(acceptor.getClass().getPackage().getName() + ":type=acceptor,name=" + acceptor.getClass().getSimpleName());
			mBeanServer.registerMBean(acceptorMBean,acceptorName);
		}catch(Exception e){
			e.printStackTrace();
		}

		LoggingFilter logFilter=new LoggingFilter();
		logFilter.setSessionOpenedLogLevel(LogLevel.DEBUG);
		logFilter.setSessionIdleLogLevel(LogLevel.NONE);
		logFilter.setMessageReceivedLogLevel(LogLevel.NONE);
		logFilter.setMessageSentLogLevel(LogLevel.NONE);
		acceptor.getFilterChain().addLast("logger",logFilter);
		//acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),LineDelimiter.WINDOWS,LineDelimiter.AUTO)));// 指定编码过滤器
		acceptor.getFilterChain().addLast("serial",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));// 指定编码过滤器
		// acceptor.getFilterChain().addLast("logger",logFilter);

		// new NamingThreadFactory("minaServer")
		// Executors.defaultThreadFactory()
		// ExecutorFilter executorFilter=new ExecutorFilter(2,16,IoEventType.MESSAGE_RECEIVED,IoEventType.MESSAGE_SENT);
		acceptor.getFilterChain().addLast("executorFilter",new ExecutorFilter(2,16,IoEventType.SESSION_OPENED,IoEventType.MESSAGE_RECEIVED,IoEventType.MESSAGE_SENT));
		// acceptor.getFilterChain().addLast("executorFilter",new ExecutorFilter());
		DefaultProfilerTimerFilter profiler=new DefaultProfilerTimerFilter();
		acceptor.getFilterChain().addFirst("profiler",new DefaultProfilerTimerFilter());
		acceptor.setHandler(new FileServerHandler());// 指定业务逻辑处理器
		acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));// 设置端口号
		acceptor.bind();// 启动监听
		log.info("Server start...");
		profiler.stat(120);
	}
}
