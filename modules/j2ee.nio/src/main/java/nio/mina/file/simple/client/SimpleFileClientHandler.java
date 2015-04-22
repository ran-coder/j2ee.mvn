package nio.mina.file.simple.client;

import nio.mina.file.Command;
import nio.mina.file.Constants;
import nio.mina.file.simple.ClientConfig;
import nio.mina.simple.BaseIoHandlerAdapter;

import org.apache.mina.core.session.IoSession;

import utils.io.nio.NioFileUtil;


/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-16 下午3:42:06
 *   
 */
public class SimpleFileClientHandler extends BaseIoHandlerAdapter{
	private ClientConfig clientConfig;

	public SimpleFileClientHandler(ClientConfig clientConfig) {
		this.clientConfig=clientConfig;
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}
	@Override  
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("Received:"+message);
		if(message!=null && message instanceof Command ){
			Command recive=(Command)message;
			if(recive.getCommand()==Constants.Cmd.INFO){
				System.out.println("recive:"+recive);
				if(recive.getLength()>0){
					
				}else{
					//session.write(new Command(Constants.Cmd.QUIT));
					//session.close(true);
				}
			}else if(recive.getCommand()==Constants.Cmd.WRITE && recive.getPath()!=null){
				if((recive.getPosition()+recive.getSize())>recive.getLength() ){
					recive.setSize(recive.getLength()-recive.getPosition());
				}
				NioFileUtil.randomWrite(clientConfig.getOutput(),recive.getBytes(),recive.getPosition());
				if((recive.getPosition()+recive.getSize())>=recive.getLength()&&recive.getLength()>0 ){
					recive.setCommand(Constants.Cmd.QUIT);
					recive.setBytes(null);
					recive.setLength(0);
					recive.setPath(null);
					recive.setPosition(0);
					recive.setSize(0);
					session.write(recive);
					session.close(true);
					return;
				}
				recive.setCommand(Constants.Cmd.READ);
				if(recive.getBytes()!=null){
					recive.setPosition(recive.getPosition()+recive.getBytes().length);
				}
				recive.setBytes(null);
				session.write(recive);
			}
		}
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("Sent:"+message);
	}
}
