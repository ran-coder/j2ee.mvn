package nio.mina.file.simple.server;

import nio.mina.file.Command;
import nio.mina.file.Constants;
import nio.mina.simple.BaseIoHandlerAdapter;

import org.apache.mina.core.session.IoSession;

import utils.io.nio.NioFileUtil;


/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-17 下午3:29:37
 *   
 */
/** handler */
class FileServerHandler extends BaseIoHandlerAdapter {
	public void sessionCreated(IoSession session) throws Exception {
		FileServer.log.info("sessionCreated:{},{}",session.getRemoteAddress());
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("Received:"+message);
		if(message!=null && message instanceof Command ){
			Command recive=(Command)message;
			if(recive.getCommand()==Constants.Cmd.QUIT){
				session.close(true);// 结束会话
				return;
			}if(recive.getCommand()==Constants.Cmd.INFO){
				recive.setCommand(Constants.Cmd.INFO);
				recive.setLength(NioFileUtil.getFileSize(recive.getPath()));
				recive.setBytes(null);
				session.write(recive);
			}else if( (recive.getCommand()==Constants.Cmd.READ||recive.getCommand()==Constants.Cmd.START)  && recive.getPath()!=null){
				if(recive.getCommand()==Constants.Cmd.START){
					recive.setLength(NioFileUtil.getFileSize(recive.getPath()));
					recive.setSize(Constants.BLOCK_SIZE);
				}
				recive.setCommand(Constants.Cmd.WRITE);
				recive.setPath(recive.getPath());
				try{
					recive.setBytes(NioFileUtil.randomRead(recive.getPath(),recive.getPosition(),recive.getSize()));
				}catch(Exception e){
					recive.setCommand(Constants.Cmd.ERROR);
					recive.setBytes(e.toString().getBytes(FileServer.CHARSET));
					session.write(recive);
					session.close(true);
				}
				session.write(recive);
			}
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		//session.close(true);
		System.out.println("Sent:"+message);
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.fillInStackTrace();

	}
}