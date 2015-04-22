package j2ee.research.java.thread.threadlocal;
/**  
 * @author yuanwei  
 * @version ctreateTime:2012-5-21 下午3:34:30
 *   
 */
public class ToppicActionTest {
	public static void main(String[] args) {
		final TopicDao dao=new TopicDao();
		new ToppicActionThread(dao).start();
		new ToppicActionThread(dao).start();
		new ToppicActionThread(dao).start();
		new ToppicActionThread(dao).start();
		new ToppicActionThread(dao).start();
	}
	static class ToppicActionThread extends Thread{
		private TopicDao dao;
		public ToppicActionThread(TopicDao dao){
			this.dao=dao;
		}
		public ToppicActionThread setConnection(){
			ConnectionUtil.setConnection(new SimpleConnection(getId()));
			return this;
		}
		public void run(){
			ConnectionUtil.setConnection(new SimpleConnection(getId()));
			for(int i=0;i<3;i++){
				dao.doQuery("sql");
			}
		}
	}
}
