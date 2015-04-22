package nio.basic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import utils.codec.ByteUtil;

public class GetChannel {
	static final int BSIZE=1024;
	public static void view(ByteBuffer buffer){   
		System.out.println("Capacity: "+buffer.capacity()
				//+",mark:"+buffer.mark()
				+", Limit: "+buffer.limit()   
				+", Posotion: "+buffer.position());   
	}  
	public static void main(String[] args) throws Exception {
		String path="C:/java/nio/data.txt";
		byte[] bytes="中文".getBytes("UTF8");
		FileChannel fc=new FileOutputStream("C:/java/nio/data.txt").getChannel();
		fc.write(ByteBuffer.wrap(bytes));
		fc.close();

		fc=new RandomAccessFile(path,"rw").getChannel();
		fc.position(fc.size());
		fc.write(ByteBuffer.wrap(bytes));
		fc.close();
		
		fc=new FileInputStream(path).getChannel();
		ByteBuffer buffer=ByteBuffer.allocate(BSIZE);
		fc.read(buffer);//写入buff
		buffer.flip();//写转读
		view(buffer);
		System.out.println(ByteUtil.toHexString(buffer.array()));
		while(buffer.hasRemaining()){
			//System.out.println((char)(bf.get()));
			System.out.print(ByteUtil.toHexString(buffer.get())+',');
			view(buffer);
		}
	}

}
