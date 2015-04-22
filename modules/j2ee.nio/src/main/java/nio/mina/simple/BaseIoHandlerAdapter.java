package nio.mina.simple;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseIoHandlerAdapter implements IoHandler {
	private static Logger log=LoggerFactory.getLogger(BaseIoHandlerAdapter.class);

	public void sessionCreated(IoSession session) throws Exception {
		log.info("sessionCreated:{}",session.getRemoteAddress());
	}

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("BaseIoHandlerAdapter.sessionOpened");
	}

	public void sessionClosed(IoSession session) throws Exception {
		log.debug("BaseIoHandlerAdapter.sessionClosed");
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("BaseIoHandlerAdapter.sessionIdle(session,'{}')",status);
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.warn("EXCEPTION, please implement {}.exceptionCaught() for proper handling:{}",getClass().getName(), cause);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		log.debug("BaseIoHandlerAdapter.messageReceived(session,'{}')",message);
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("BaseIoHandlerAdapter.messageSent(session)");
	}
}
