package j2ee.research.fw.activemq.com.fltrp.activemq.converter;

import j2ee.research.fw.activemq.com.fltrp.activemq.consumer.MyMessage;

import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.support.converter.MessageConverter;

public class DefaultMessageConverter implements MessageConverter {

	private Log logger = LogFactory.getLog(DefaultMessageConverter.class);

	/**
	 * 
	 * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object,
	 *      javax.jms.Session)
	 */
	public Message toMessage(Object obj, Session session) throws JMSException {
		// check Type
		ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage) session.createObjectMessage();
		HashMap<String, MyMessage> map = new HashMap<String, MyMessage>();
		MyMessage mymessage = (MyMessage)obj;
		map.put("MyMessage", mymessage);
		objMsg.setObjectProperty("Map", map);
		return objMsg;
	}

	/**
	 * 
	 * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(javax.jms.Message)
	 */
	public Object fromMessage(Message msg) throws JMSException {
		
		if (msg instanceof ObjectMessage) {
			HashMap<?, ?> map = (HashMap<?, ?>) ((ObjectMessage) msg).getObjectProperty("Map");
			return (MyMessage)map.get("MyMessage");
		} else {
			logger.error("Msg:[" + msg + "] is not Map", new JMSException("Msg:[" + msg
					+ "] is not Map"));
		}

		return null;
	}

}
