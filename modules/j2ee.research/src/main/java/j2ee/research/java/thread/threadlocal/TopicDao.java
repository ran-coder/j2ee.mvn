package j2ee.research.java.thread.threadlocal;


/**  
 * @author yuanwei  
 * @version ctreateTime:2012-5-21 下午3:25:14
 *   
 */
public class TopicDao {
	public void doQuery(String sql){
		ConnectionUtil.getConnection().createStatement().executeQuery(sql);
	}
}
