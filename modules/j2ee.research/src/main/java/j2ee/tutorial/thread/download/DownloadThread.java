package j2ee.tutorial.thread.download;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DownloadThread extends Thread {
	protected final Log log=LogFactory.getLog(getClass());
	/** 待下载的文件 */
	private String	url		=null;
	private String	file	=null;	// 本地存储路径
	private long	offset	=0;	// 偏移量
	private long	length	=0;	// 分配给本线程的下载字节数

	public DownloadThread(String url, String file, long offset, long length) {
		this.url=url;
		this.file=file;
		this.offset=offset;
		this.length=length;
		//System.out.println("偏移量=" + offset + ";字节数=" + length);
		if(log.isDebugEnabled())log.debug("偏移量=" + offset + ";字节数=" + length);
	}

	public void run() {
		try{
			HttpURLConnection httpConnection=(HttpURLConnection)new URL(this.url).openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("RANGE","bytes=" + this.offset + "-" + (this.offset + this.length - 1));
			BufferedInputStream bis=new BufferedInputStream(httpConnection.getInputStream());
			byte[] buff=new byte[1024]; // 分段读取，防止内容泄露
			int bytesRead;
			while(-1 != (bytesRead=bis.read(buff,0,buff.length))){
				this.writeFile(this.file,this.offset,buff,bytesRead);
				this.offset=this.offset + bytesRead;
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	// 将字节数组以随机存取方式写入文件
	// fileName是被写入的文件
	// offset代表写入文件的位置偏移量
	// bytes是待写入的字节数组
	// realLength是实际需要写入的字节数（realLength<=bytes.length）
	private void writeFile(String fileName, long offset, byte[] bytes, int realLength) throws IOException {
		File newFile=new File(fileName);
		RandomAccessFile raf=new RandomAccessFile(newFile,"rw");
		raf.seek(offset);
		raf.write(bytes,0,realLength);
		raf.close();
	}
}
