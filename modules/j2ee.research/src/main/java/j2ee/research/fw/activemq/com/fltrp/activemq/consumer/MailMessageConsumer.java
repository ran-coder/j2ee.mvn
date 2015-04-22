package j2ee.research.fw.activemq.com.fltrp.activemq.consumer;

import j2ee.research.fw.activemq.com.fltrp.service.MailEngine;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;

public class MailMessageConsumer implements MyConsumer{



	private MailEngine mailEngine;
	protected final transient Log log = LogFactory.getLog(getClass());
	public void onMessage(Object model) {
		Map<?, ?> m=(Map<?, ?>)model;
		SimpleMailMessage message = (SimpleMailMessage) m.get("message");
		try {
			mailEngine.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MailEngine getMailEngine() {
		return mailEngine;
	}

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	public void after() {
		log.info("----------into "+this.getClass().getName());
		log.info("----------in "+ this.getClass().getName() +" complete time is" + new Date());
	}

	public void before(MyMessage mymessage) {
		log.info("----------into "+this.getClass().getName());
		log.info("----------send time is" + mymessage.getSendTime());
	}


}
