package j2ee.research.java.thread.threadlocal;


/**  
 * @author yuanwei  
 * @version ctreateTime:2012-5-21 下午3:48:22
 *   
 */
public class ConnectionUtil {
	private static final ThreadLocal<Connection>	connections=new ThreadLocal<Connection>();
	public static Connection getConnection(){
		return connections.get();
	}
	public static void setConnection(Connection conn){
		connections.set(conn);
	}
}
