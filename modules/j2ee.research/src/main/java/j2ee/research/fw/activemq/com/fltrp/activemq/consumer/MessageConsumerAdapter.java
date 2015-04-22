package j2ee.research.fw.activemq.com.fltrp.activemq.consumer;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.DateUtil;

public class MessageConsumerAdapter {

	@SuppressWarnings("unused")
	private MyConsumer myConsumer;
	@SuppressWarnings("rawtypes")
	private Map consumerMap;
	
	protected final transient Log log = LogFactory.getLog(getClass());
	
	public void onMessage(MyMessage mc) throws Exception {
		
		Object model = mc.getModel();
		String type = mc.getType();
		log.info("---------consumerMap key is " + type);
		log.info("---------Time when enter the MessageConsumerAdapter is "+ DateUtil.toString(new Date()));
		//log.info("model content is" + model);
		MyConsumer c = (MyConsumer)consumerMap.get(type);
		c.before(mc);
		((MyConsumer)consumerMap.get(type)).onMessage(model);
		c.after();
		
//		try {
//			ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-jms.xml");
//            if (ctx != null) {
//            	myConsumer = (MyConsumer)ctx.getBean(type);
//            	myConsumer.onMessage(model);
//            	
//            	
//            	log.info("receiver is " + type);
//            	log.info("sendTime is " +new Date());
//            }
//        } catch (NoSuchBeanDefinitionException n) {}
	}


	public void setMyConsumer(MyConsumer myConsumer) {
		this.myConsumer = myConsumer;
	}



	@SuppressWarnings("rawtypes")
	public void setConsumerMap(Map consumerMap) {
		this.consumerMap = consumerMap;
	}

	

}
