package j2ee.research.fw.activemq.com.fltrp.activemq.producer;

import javax.jms.Queue;

import org.springframework.jms.core.JmsTemplate;

import j2ee.research.fw.activemq.com.fltrp.activemq.consumer.MyMessage;

public class MailMessageProducer {

	private JmsTemplate template;

	private Queue destination;

	/**
	 * @param template
	 */
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	/**
	 * @param destination
	 */
	public void setDestination(Queue destination) {
		this.destination = destination;
	}

	public void send(MyMessage mymessage) {
		template.convertAndSend(this.destination, mymessage);
	}
}
