package j2ee.research.fw.activemq.com.fltrp.service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Class for sending e-mail messages based on Velocity templates or with attachments.
 * <p>
 * <a href="MailEngine.java.html"><i>View Source</i></a>
 * </p>
 * @author Matt Raible
 */
public class MailEngine {
	protected static final Log	log	=LogFactory.getLog(MailEngine.class);
	private MailSender					mailSender;
	private VelocityEngine			velocityEngine;
	private String							defaultReceiver;
	private String							doesSend;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender=mailSender;
	}
	
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine=velocityEngine;
	}
	
	public void setDoesSend(String doesSend) {
		this.doesSend=doesSend;
	}
	
	public void setDefaultReceiver(String receiver) {
		defaultReceiver=receiver;
	}
	
	/**
	 * Send a simple message based on a Velocity template.
	 * @param msg
	 * @param templateName
	 * @param model
	 */
	public void sendMessage(final SimpleMailMessage mailMessage, final String templateName, final Map<String, Object> model) throws MessagingException {
		if(doesSend != null && doesSend.equals("false")){ return; }
		MimeMessagePreparator preparator=new MimeMessagePreparator() {
			
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message=new MimeMessageHelper(mimeMessage,"UTF-8");
				if(defaultReceiver == null || defaultReceiver.equals("")){
					message.setTo(mailMessage.getTo());
				}else{
					message.setTo(defaultReceiver);
				}
				message.setFrom(mailMessage.getFrom());
				message.setSubject(mailMessage.getSubject());
				
				String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,templateName,model);
				message.setText(text,true);
			}
		};
		
		((JavaMailSenderImpl)mailSender).send(preparator);
	}
	
	/**
	 * Send a simple message with pre-populated values.
	 * @param msg
	 */
	public void send(SimpleMailMessage msg) {
		try{
			mailSender.send(msg);
		}catch(MailException ex){
			// log it and go on
			log.error(ex.getMessage(),ex);
		}
	}
	
	/**
	 * Convenience method for sending messages with attachments.
	 * @param emailAddresses
	 * @param resource
	 * @param bodyText
	 * @param subject
	 * @param attachmentName
	 * @throws MessagingException
	 * @author Ben Gill
	 */
	public void sendMessage(String[] emailAddresses, ClassPathResource resource, String bodyText, String subject, String attachmentName) throws MessagingException {
		MimeMessage message=((JavaMailSenderImpl)mailSender).createMimeMessage();
		
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper=new MimeMessageHelper(message,true);
		
		helper.setTo(emailAddresses);
		helper.setText(bodyText);
		helper.setSubject(subject);
		helper.addAttachment(attachmentName,resource);
		((JavaMailSenderImpl)mailSender).send(message);
	}
}
