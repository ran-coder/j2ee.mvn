package nio.mina.file;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-16 下午4:00:45
 *   
 */
public class Constants {
	public static final long BLOCK_SIZE=16;
	public static class Cmd{
		public transient static final byte	START	=-4;
		public transient static final byte	DONE	=-3;
		public transient static final byte	ERROR	=-2;
		public transient static final byte	QUIT	=-1;
		public transient static final byte	READ	=0;
		public transient static final byte	WRITE	=1;
		public transient static final byte	INFO	=2;
	}
}
