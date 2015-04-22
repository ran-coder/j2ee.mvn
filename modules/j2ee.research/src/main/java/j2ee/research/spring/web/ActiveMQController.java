package j2ee.research.spring.web;

import j2ee.research.fw.activemq.com.fltrp.activemq.consumer.MyMessage;
import j2ee.research.fw.activemq.com.fltrp.activemq.producer.DefaultProducer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
//@RequestMapping("/mail")
public class ActiveMQController {
	private static Logger log=LoggerFactory.getLogger(ActiveMQController.class);

	@Resource
	private DefaultProducer defaultProducer;
	@RequestMapping("/jms.do")
	@ResponseBody
	public String jms(){
		log.info("jms start..");
		MyMessage mymessage = new MyMessage();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("email","yw2325@163.com");
		mymessage.setModel(map);
		mymessage.setType("defaultConsumer");
		mymessage.setSendTime(new Date());
		defaultProducer.send(mymessage);
		return "true";
	}

}
