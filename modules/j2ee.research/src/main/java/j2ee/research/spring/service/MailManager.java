package j2ee.research.spring.service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MailManager {
	private static Logger log=LoggerFactory.getLogger(MailManager.class);
	private MailSender			mailSender;
	private VelocityEngine		velocityEngine;
	private String				defaultFrom;
	private String				defaultReceiver;
	private String				doesSend;
	
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
	public void setDefaultFrom(String defaultFrom) {
		this.defaultFrom=defaultFrom;
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
			log.error(ex.getMessage());
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
	public void sendMessage(String bodyText, String subject, String attachmentName, ClassPathResource resource,String... emailAddresses) throws MessagingException {
		MimeMessage message=((JavaMailSenderImpl)mailSender).createMimeMessage();
		
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper=new MimeMessageHelper(message,true);
		
		helper.setTo(emailAddresses);
		helper.setText(bodyText);
		helper.setSubject(subject);
		helper.addAttachment(attachmentName,resource);
		((JavaMailSenderImpl)mailSender).send(message);
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
	public void sendMessage(String emailAddresses,String subject,String bodyText) throws MessagingException {
		MimeMessage message=((JavaMailSenderImpl)mailSender).createMimeMessage();
		
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper=new MimeMessageHelper(message,true);
		
		helper.setFrom(defaultFrom);
		helper.setTo(emailAddresses);
		helper.setText(bodyText);
		helper.setSubject(subject);
		((JavaMailSenderImpl)mailSender).send(message);
	}
}
