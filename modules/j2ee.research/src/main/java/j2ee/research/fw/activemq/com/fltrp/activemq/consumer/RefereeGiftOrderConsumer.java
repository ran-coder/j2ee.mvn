package j2ee.research.fw.activemq.com.fltrp.activemq.consumer;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.velocity.VelocityEngineUtils;

import utils.CollectionUtil;
import utils.DateUtil;
import utils.framework.SpringPropertyUtil;

import j2ee.research.fw.activemq.com.fltrp.service.MailEngine;

public class RefereeGiftOrderConsumer implements MyConsumer{
	private final transient Log log = LogFactory.getLog(getClass());
	private MailEngine mailEngine;
	private VelocityEngine velocityEngine;
	private SimpleMailMessage mailMessage;
	public MailEngine getMailEngine() {
		return mailEngine;
	}
	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine=mailEngine;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine=velocityEngine;
	}
	public SimpleMailMessage getMailMessage() {
		return mailMessage;
	}
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage=mailMessage;
	}
	public void onMessage(Object model) {
		@SuppressWarnings("unchecked")
		Map<String,Object> map=(Map<String,Object>)model;
		String email=(String)CollectionUtil.getMapValue(map,"email",null);
		String mobile=(String)CollectionUtil.getMapValue(map,"mobile",null);
		boolean debug="true".equalsIgnoreCase(SpringPropertyUtil.getProperty("recommendation.jms.debug","false"));
		if(log.isInfoEnabled()){
			log.info(new StringBuilder()
				.append("debug:").append(debug).append(',')
				.append("email:").append(email).append(',')
				.append("mobile:").append(mobile).toString());
		}
		if(debug){
			email=SpringPropertyUtil.getProperty("recommendation.jms.debug.email",null);
			mobile=SpringPropertyUtil.getProperty("recommendation.jms.debug.mobile",null);
		}
		//ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, new Locale("zh", "CN"));
		//map.put("resourceBundle",resourceBundle);
		if(!StringUtils.isEmpty(email)){
			String text=null;
			try{
				text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,"refereeGiftOrderEmail.vm","UTF-8", map);
			}catch(Exception e){
				log.error("VelocityEngineUtils Exception:",e);
			}
			if(!StringUtils.isEmpty(text)){
				//SimpleMailMessage mailMessage=new SimpleMailMessage();
				//mailMessage.setSubject(resourceBundle.getString("shiporder.email.subject"));
				mailMessage.setText(text);
				mailMessage.setTo(email);
				mailEngine.send(mailMessage);
				log.info("sendMail:"+email);
			}
		}/**/
		if(!StringUtils.isEmpty(mobile)){
			String text=null;
			try{
				text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,"refereeGiftOrderSMS.vm","UTF-8", map);
			}catch(Exception e){
				text=null;
				log.error("VelocityEngineUtils Exception:",e);
				//e.printStackTrace();
			}
			if(!StringUtils.isEmpty(text))
				log.info("sendSMS:"+mobile);
				//sendSMSManager.sendSMS(mobile,text);
		}
	}

	public void after() {
		log.info("----------into "+this.getClass().getName());
		log.info("----------in "+ this.getClass().getName() +" complete time is" + DateUtil.toString(new Date()));
	}

	public void before(MyMessage mymessage) {
		log.info("----------into "+this.getClass().getName());
		log.info("----------send time is" + DateUtil.toString(mymessage.getSendTime()));
	}


}
