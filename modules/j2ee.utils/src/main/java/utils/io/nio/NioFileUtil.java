package utils.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import utils.codec.ByteUtil;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

/**
 * @author yuanwei
 * @version ctreateTime:2011-11-14 下午5:33:14
 */
public class NioFileUtil {
	public static long getFileSize(String path){
		File file=new File(path);
		if(file==null||!file.exists()){
			return -1;
		}
		return file.length();
	}
	public static byte[] randomRead(String path, long offset, long length) throws IOException {
		return guavaRead(path,offset,length);
		/*RandomAccessFile file=new RandomAccessFile(path,"rw");
		FileChannel fc=file.getChannel();
		MappedByteBuffer buff=fc.map(FileChannel.MapMode.READ_WRITE,position,size);
		try{
			return ByteUtil.getBytes(buff.slice());
		}finally{
			fc.close();
		}*/
	}

	public static void randomWrite(String path, byte[] src, long position) throws IOException {
		File file=new File(path);
		if(!file.exists() && !file.createNewFile()){ throw new FileNotFoundException(path); }
		RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
		FileChannel fc=randomAccessFile.getChannel();
		fc.write(ByteBuffer.wrap(src),position);
		fc.close();
	}
	public static byte[] guavaRead(String path,final long offset,final long length){
		InputSupplier<FileInputStream> fileIn=Files.newInputStreamSupplier(new File(path));
		InputSupplier<InputStream> slicedStream=ByteStreams.slice(fileIn,offset,length);
		byte[] data=null;
		try{
			data=ByteStreams.toByteArray(slicedStream);
			return data;
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	public static void guavaWrite(String path, byte[] src, long position, long size) throws IOException{
		File file=null;
		try{
			file=new File(path);
		}catch(Exception e){
			throw new FileNotFoundException(path);
		}
		if(file==null|| (!file.exists() && !file.createNewFile()) ){
			throw new FileNotFoundException(path);
		}
		Files.write(src,file);
	}
	
	public static String md5(String path) throws IOException{
		File file=null;
		try{
			file=new File(path);
		}catch(Exception e){
			throw new FileNotFoundException(path);
		}
		if(file==null|| (!file.exists() && !file.createNewFile()) ){
			throw new FileNotFoundException(path);
		}
		FileChannel fc=new RandomAccessFile(path,"r").getChannel();
		ByteBuffer buff=ByteBuffer.allocate(1024);
		MessageDigest messageDigest=null;
		try{
			messageDigest = MessageDigest.getInstance("MD5");
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		if(messageDigest==null)return null;
		while(fc.read(buff)>0){//0 or -1
			buff.flip();
			messageDigest.update(buff);
			buff.clear();
		}
		byte[] digest=messageDigest.digest();
		int j = digest.length;
		char[] chars = new char[j * 2];
		int k = 0;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		for (int i = 0; i < j; i++) {
			chars[k++] = hexDigits[(digest[i] >>> 4) & 0xf];
			chars[k++] = hexDigits[digest[i] & 0xf];
		}
		return new String(chars);
	}
	public static void main(String[] args) throws IOException {
		System.out.println(ByteUtil.toHexStringWithoutPrefix(randomRead("C:/java/nio/stacktest.txt",0,20)));
		System.out.println(ByteUtil.toHexStringWithoutPrefix(guavaRead("C:/java/nio/stacktest.txt",0,20)));
		System.out.println(md5("C:/java/nio/stacktest.txt"));
		System.out.println(md5("C:/java/nio/stacktest1.txt"));
	}
}
