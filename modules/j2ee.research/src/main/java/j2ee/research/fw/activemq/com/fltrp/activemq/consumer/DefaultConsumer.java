package j2ee.research.fw.activemq.com.fltrp.activemq.consumer;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.DateUtil;

public class DefaultConsumer implements MyConsumer{
	private final transient Log log = LogFactory.getLog(getClass());
	public void onMessage(Object model) {
		log.info(model);
		//System.out.println(model);
	}

	public void after() {
		//log.info("----------into "+this.getClass().getName());
		log.info("----------in "+ this.getClass().getName() +" complete time is:" + DateUtil.toString(new Date()));
	}

	public void before(MyMessage mymessage) {
		//log.info("----------into "+this.getClass().getName());
		log.info("----------send time is:" + DateUtil.toString(mymessage.getSendTime()));
	}


}
