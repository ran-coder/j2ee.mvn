package tutorial.apache.util.net;

import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;

public class SMTPTest {
	public static void smtp() throws Exception {
		SMTPClient client=new SMTPClient();
		client.connect("www.discursive.com");
		int response=client.getReplyCode();
		if(SMTPReply.isPositiveCompletion(response)){
			// Set the sender and the recipients
			client.setSender("yuanwei@163.com");
			client.addRecipient("yw2325@qq.com");
			// Supply the message via a Writer
			Writer message=client.sendMessageData();
			message.write("Spend more money on energy research.  Thanks.");
			message.close();
			// Send the message and print a confirmation
			boolean success=client.completePendingCommand();
			if(success){
				System.out.println("Message sent");
			}
		}else{
			System.out.println("Error communicating with SMTP server");
		}
		client.disconnect();
	}

	public static void pop() throws Exception {
		POP3Client client=new POP3Client();
		client.connect("pop.qq.com");
		client.login("yw2325@qq.com","39282617");

		POP3MessageInfo[] messages=client.listMessages();
		for(int i=0;i < messages.length;i++){
			int messageNum=messages[i].number;
			System.out.println("************* Message number: " + messageNum);
			Reader reader=client.retrieveMessage(messageNum);
			System.out.println("Message:\n" + IOUtils.toString(reader));
			IOUtils.closeQuietly(reader);
		}

		client.logout();
		client.disconnect();

	}

	public static void main(String[] args) throws Exception {
		pop();
	}
}
