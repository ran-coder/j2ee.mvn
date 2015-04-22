package j2ee.research.fw.activemq.com.fltrp.activemq.producer;

import javax.jms.Queue;

import org.springframework.jms.core.JmsTemplate;

import j2ee.research.fw.activemq.com.fltrp.activemq.consumer.MyMessage;

public class DefaultProducer {
	private JmsTemplate jmsTemplate;
	private Queue destination;
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate=jmsTemplate;
	}
	public void setDestination(Queue destination) {
		this.destination = destination;
	}
	public void send(MyMessage mymessage) {
		jmsTemplate.convertAndSend(this.destination, mymessage);
	}
}
