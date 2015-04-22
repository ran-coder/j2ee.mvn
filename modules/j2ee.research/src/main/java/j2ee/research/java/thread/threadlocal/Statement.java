package j2ee.research.java.thread.threadlocal;


/**
 * @author yuanwei
 * @version ctreateTime:2012-5-21 下午3:14:57
 */
public interface Statement {
	public void executeQuery(String sql);
	public void close();
}
