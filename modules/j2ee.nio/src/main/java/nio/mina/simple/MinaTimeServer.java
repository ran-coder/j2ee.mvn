package nio.mina.simple;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.statistic.ProfilerTimerFilter;
import org.apache.mina.integration.jmx.IoServiceMBean;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.DateUtil;

public class MinaTimeServer {
	static Logger log=LoggerFactory.getLogger(MinaTimeServer.class);
	private static final int	PORT	=9090;	// 定义监听端口

	/** handler */
	static class TimeServerHandler extends BaseIoHandlerAdapter {
		public void sessionCreated(IoSession session) throws Exception {
			log.info("sessionCreated:{},{}",session.getRemoteAddress(),session.getAttribute("JSESSION"));
		}
		@Override
		public void messageReceived(IoSession session, Object message) throws Exception {
			System.out.println(session.getAttribute("JSESSION"));
			String str=message==null?null:message.toString().trim();
			if(str!=null&&str.equalsIgnoreCase("quit")){
				session.close(true);// 结束会话
				return;
			}
			//try{TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e){}
			Date date=new Date();
			session.write(DateUtil.toString(date));// 返回当前时间的字符串
			session.write('\n');
			log.debug("Message written...");
		}
		@Override
		public void messageSent(IoSession session, Object message) throws Exception {
			session.close(true);
		}
	}

	public static void main(String[] args) throws IOException {
		NioSocketAcceptor acceptor=new NioSocketAcceptor();
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer(); 
		IoServiceMBean acceptorMBean = new IoServiceMBean(acceptor); 
		ObjectName acceptorName=null;
		try{
			acceptorName=new ObjectName(acceptor.getClass().getPackage().getName()+ ":type=acceptor,name=" + acceptor.getClass().getSimpleName());
			mBeanServer.registerMBean(acceptorMBean, acceptorName);
		}catch(Exception e){
			e.printStackTrace();
		}

		LoggingFilter logFilter=new LoggingFilter();
		logFilter.setSessionOpenedLogLevel(LogLevel.DEBUG);
		//acceptor.getFilterChain().addLast("logger",logFilter);
		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),LineDelimiter.WINDOWS,LineDelimiter.AUTO)));// 指定编码过滤器
		//acceptor.getFilterChain().addLast("logger",logFilter);

		//new NamingThreadFactory("minaServer")
		//Executors.defaultThreadFactory()
		//ExecutorFilter executorFilter=new ExecutorFilter(2,16,IoEventType.MESSAGE_RECEIVED,IoEventType.MESSAGE_SENT);
		acceptor.getFilterChain().addLast("executorFilter",new ExecutorFilter(2,16,IoEventType.SESSION_OPENED,IoEventType.MESSAGE_RECEIVED,IoEventType.MESSAGE_SENT));
		//acceptor.getFilterChain().addLast("executorFilter",new ExecutorFilter());
		ProfilerTimerFilter profiler=new ProfilerTimerFilter(TimeUnit.MILLISECONDS,
				IoEventType.SESSION_OPENED,IoEventType.MESSAGE_RECEIVED,
				IoEventType.MESSAGE_SENT,IoEventType.SESSION_CREATED,
				IoEventType.SESSION_OPENED,IoEventType.SESSION_IDLE,
				IoEventType.SESSION_CLOSED);
		acceptor.getFilterChain().addFirst("Profiler",profiler);
		acceptor.setHandler(new TimeServerHandler());// 指定业务逻辑处理器
		acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));// 设置端口号
		acceptor.bind();//启动监听
		log.info("Server start...");
		for(;;){
			try{Thread.sleep(60000);}catch(InterruptedException e){}
			System.out.println("calls	TotalTime	min	max");
			for(IoEventType type:profiler.getEventsToProfile()){
				System.out.println(
					profiler.getTotalCalls(type)+"	"+profiler.getTotalTime(type)+"	"+
							profiler.getMinimumTime(type)+"	"+profiler.getMaximumTime(type)+":"+type.name());
			}
		}
	}

}