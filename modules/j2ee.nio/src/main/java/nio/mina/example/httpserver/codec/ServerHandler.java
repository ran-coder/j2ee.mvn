package nio.mina.example.httpserver.codec;

import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An {@link IoHandler} for HTTP.
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 588178 $, $Date: 2007-10-25 18:28:40 +0900 (목, 25 10월 2007) $
 */
public class ServerHandler extends IoHandlerAdapter {
	private static Logger log=LoggerFactory.getLogger(ServerHandler.class);
	@Override
	public void sessionOpened(IoSession session) {
		// set idle time to 60 seconds
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE,60);
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		// Check that we can service the request context
		HttpResponseMessage response=new HttpResponseMessage();
		response.setContentType("text/plain");
		response.setResponseCode(HttpResponseMessage.HTTP_STATUS_SUCCESS);
		response.appendBody("CONNECTED");

		// msg.setResponseCode(HttpResponseMessage.HTTP_STATUS_SUCCESS);
		// byte[] b = new byte[ta.buffer.limit()];
		// ta.buffer.rewind().get(b);
		// msg.appendBody(b);
		// System.out.println("####################");
		// System.out.println("  GET_TILE RESPONSE SENT - ATTACHMENT GOOD DIAMOND.SI="+d.si+
		// ", "+new java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss.SSS").format(new java.util.Date()));
		// System.out.println("#################### - status="+ta.state+", index="+message.getIndex());

		// // Unknown request
		// response = new HttpResponseMessage();
		// response.setResponseCode(HttpResponseMessage.HTTP_STATUS_NOT_FOUND);
		// response.appendBody(String.format(
		// "<html><body><h1>UNKNOWN REQUEST %d</h1></body></html>",
		// HttpResponseMessage.HTTP_STATUS_NOT_FOUND));

		if(response != null){
			session.write(response).addListener(IoFutureListener.CLOSE);
		}
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		log.info("Disconnecting the idle.");
		session.close(false);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		log.warn("exceptionCaught",cause);
		session.close(false);
	}
}