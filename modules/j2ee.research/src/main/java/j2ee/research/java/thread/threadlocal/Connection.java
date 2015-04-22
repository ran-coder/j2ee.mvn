package j2ee.research.java.thread.threadlocal;



/**
 * @author yuanwei
 * @version ctreateTime:2012-5-21 下午2:36:59
 */
public interface Connection {
	public Statement createStatement();
	public void close();
}
