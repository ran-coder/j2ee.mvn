package nio.basic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import utils.codec.ByteUtil;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-10-26 下午3:47:04
 *   
 */
public class FileExample {
	static final String path="C:/java/nio/MappedIO.tmp";
	static final String path1="C:/java/nio/MappedIO.t";
	static final String path2="C:/java/nio/data.txt";
	static final String png="C:/java/nio/1.png";
	static final String png2="C:/java/nio/2.png";
	static final String out="C:/java/nio/out.t";
	public static void writeFile() throws IOException{
		System.out.println("****************** writeFile ******************");
		ByteBuffer buff=ByteBuffer.allocate(1024);
		FileChannel fc=new FileOutputStream(out).getChannel();
		for(int i=0;i<1024;i++){
			buff.put((byte)(i&0xff));
		}
		buff.flip();
		fc.write(buff);
		fc.close();
	}
	public static void readFile() throws IOException{
		System.out.println("****************** readFile ******************");
		ByteBuffer buff=ByteBuffer.allocate(128);
		FileChannel fc=new FileInputStream(out).getChannel();
		while(fc.read(buff)!=-1){
			buff.flip();
			System.out.println(ByteUtil.toHexString(buff.array()));
			buff.clear();
		};
		fc.close();
	}
	public static void randomReadFile() throws IOException{
		System.out.println("****************** randomReadFile ******************");
		ByteBuffer buff=ByteBuffer.allocate(128);
		FileChannel fc=new RandomAccessFile(png,"r").getChannel();
		fc.read(buff);
		System.out.println(ByteUtil.toHexString(buff.array()));
		fc.close();
	}
	public static void copyFile() throws IOException{
		System.out.println("****************** copyFile ******************");
		ByteBuffer buff=ByteBuffer.allocate(1024);
		FileChannel fin=new FileInputStream(png).getChannel();
		FileChannel fout=new FileOutputStream(png2).getChannel();
		while(fin.read(buff)!=-1){
			buff.flip();
			fout.write(buff);
			buff.clear();
		};
		fin.close();
		fout.close();
	}
	public static void main(String[] args) throws IOException {
		writeFile();
		readFile();
		randomReadFile();
		copyFile();
	}

}
