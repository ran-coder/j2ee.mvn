package j2ee.research.java.nio.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import utils.ExceptionUtil;
import utils.test.Tester;

/**
 * @author yuanwei
 * @version ctreateTime:2011-10-25 上午9:47:56
 */
public class MappedIO {
	static int		numOfInts		=4000000;
	static int		numOfBuffInts	=200000;
	static String path="C:/java/nio/MappedIO.tmp";
	static Tester[]	tests			={
		new Tester("Stream Write"){
			public void test() throws IOException {
				DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
				for(int i=0;i < numOfInts;i++)
					dos.writeInt(i);
				dos.close();
			}
		},
		new Tester("Stream Read") {
			public void test() throws IOException {
				DataInputStream dis=new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
				for(int i=0;i < numOfInts;i++)
					dis.readInt();
				dis.close();
			}
		},
		new Tester("Stream Read/Write") {
			public void test() throws IOException {
				RandomAccessFile raf=new RandomAccessFile(path,"rw");
				raf.writeInt(1);
				for(int i=0;i < numOfBuffInts;i++){
					raf.seek(raf.length() - 4);
					raf.writeInt(raf.readInt());
				}
				raf.close();
			}
		},
		new Tester("Mapped Write") {
			public void test() throws IOException {
				FileChannel fc=new RandomAccessFile(path,"rw").getChannel();
				IntBuffer ibuff=fc.map(FileChannel.MapMode.READ_WRITE,0,fc.size()).asIntBuffer();
				for(int i=0;i < numOfInts;i++)
					ibuff.put(i);
				fc.close();
			}
		},
		new Tester("Mapped Read") {
			public void test() throws IOException {
				FileChannel fc=new FileInputStream(path).getChannel();
				IntBuffer ibuff=fc.map(FileChannel.MapMode.READ_ONLY,0,fc.size()).asIntBuffer();
				while(ibuff.hasRemaining())
					ibuff.get();
				fc.close();
			}
		},
		new Tester("Mapped Read/Write") {
			public void test() throws IOException {
				FileChannel fc=new RandomAccessFile(path,"rw").getChannel();
				IntBuffer ibuff=fc.map(FileChannel.MapMode.READ_WRITE,0,fc.size()).asIntBuffer();
				ibuff.put(0);
				for(int i=1;i < numOfBuffInts;i++)
					ibuff.put(ibuff.get(i - 1));
				fc.close();
			}
		},
		new Tester("Locking Mapped Read/Write ") {
			public void test() throws IOException {
				FileChannel fc=new RandomAccessFile(path,"rw").getChannel();
				FileLock lock=fc.tryLock();
				if(lock==null){//文件已锁
					throw ExceptionUtil.newRuntimeException("file is lock!");
				}
				IntBuffer ibuff=fc.map(FileChannel.MapMode.READ_WRITE,0,fc.size()).asIntBuffer();
				ibuff.put(0);
				for(int i=1;i < numOfBuffInts;i++)
					ibuff.put(ibuff.get(i - 1));
				lock.release();
				fc.close();
			}
		}
	};
	public static void main(String[] args) {
		for(Tester test : tests)
			test.run(Tester.SEC);
	}
}
