package nio.basic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-10-24 下午3:12:12
 *   
 */
public class BuffToText {
	static final int BSIZE=1024;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String path="C:/java/nio/data1.txt";
		FileChannel fc=new FileOutputStream(path).getChannel();
		fc.write(ByteBuffer.wrap("data1".getBytes()));
		fc.close();

		fc=new FileInputStream(path).getChannel();
		ByteBuffer buff=ByteBuffer.allocate(BSIZE);
		fc.read(buff);
		buff.flip();
		System.out.println(buff.asCharBuffer());

		buff.rewind();
		String encoding=System.getProperty("file.encoding");
		System.out.println("encoding:"+encoding);
		System.out.println("Decode using "+encoding+":"+Charset.forName(encoding).decode(buff));

		fc=new FileOutputStream(path).getChannel();
		fc.write(ByteBuffer.wrap("data1中文".getBytes("UTF-16BE")));
		fc.close();

		fc=new FileInputStream(path).getChannel();
		buff.clear();
		fc.read(buff);
		buff.flip();
		System.out.println(buff.asCharBuffer());

		fc=new FileOutputStream(path).getChannel();
		buff=ByteBuffer.allocate(24);
		buff.asCharBuffer().put("data2中文");
		fc.write(buff);
		fc.close();

		fc=new FileInputStream(path).getChannel();
		buff.clear();
		fc.read(buff);
		buff.flip();
		System.out.println(buff.asCharBuffer());
	}

}
