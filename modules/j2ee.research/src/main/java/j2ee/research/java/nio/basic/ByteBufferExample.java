package j2ee.research.java.nio.basic;

import java.nio.ByteBuffer;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-10-24 下午5:23:15
 *   
 */
public class ByteBufferExample {
	public static void view(ByteBuffer buffer){   
		System.out.println("Capacity: "+buffer.capacity()
				//+",mark:"+buffer.markmarkValue
				+", Limit: "+buffer.limit()   
				+", Posotion: "+buffer.position());   
	}
	public static void main(String[] args) throws Exception {
		ByteBuffer buffer=ByteBuffer.allocate(128);
		buffer.put("中文".getBytes("UTF8"));
		System.out.println("********** flip **********");
		view(buffer);
		buffer.flip();
		view(buffer);
		buffer.clear();
		System.out.println("********** buffer **********");
		view(buffer);
		buffer.put("中文".getBytes("UTF8"));
		System.out.println("********** rewind **********");
		view(buffer);
		buffer.rewind();
		view(buffer);
	}
}
