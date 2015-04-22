package j2ee.tutorial.thread.download;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

public class DownloadManager {
	static final long	unitSize	=100 * 1024;	// 分配给每个下载线程的字节数

	public static void main(String[] args) throws IOException {
		if(args.length != 2){
			System.out.println("Usage java DownloadManager URL local_file_name");
			return;
		}
		DownloadManager downloadManager=new DownloadManager();
		downloadManager.doDownload(args[0],args[1]);
	}

	public void doDownload(String remoteFileUrl, String localFileName) throws IOException {
		long fileSize=this.getRemoteFileSize(remoteFileUrl);
		this.createFile(localFileName,fileSize);
		long threadCount=fileSize / unitSize;
		System.out.println("共启动线程" + (fileSize % unitSize == 0 ? threadCount : threadCount + 1) + "个");
		long offset=0;
		if(fileSize <= unitSize){ // 如果远程文件尺寸小于等于unitSize
			DownloadThread downloadThread=new DownloadThread(remoteFileUrl,localFileName,offset,fileSize);
			downloadThread.start();
		}else{ // 如果远程文件尺寸大于unitSize
			for(int i=1;i <= threadCount;i++){
				DownloadThread downloadThread=new DownloadThread(remoteFileUrl,localFileName,offset,unitSize);
				downloadThread.start();
				offset=offset + unitSize;
			}
			if(fileSize % unitSize != 0){// 如果不能整除，则需要再创建一个线程下载剩余字节
				DownloadThread downloadThread=new DownloadThread(remoteFileUrl,localFileName,offset,fileSize - unitSize * threadCount);
				downloadThread.start();
			}
		}
	}

	// 获取远程文件尺寸
	private long getRemoteFileSize(String remoteFileUrl) throws IOException {
		long result=0;
		HttpURLConnection httpConnection=(HttpURLConnection)new URL(remoteFileUrl).openConnection();
		httpConnection.setRequestMethod("HEAD");
		for(int i=1;i <= 10;i++){
			if(httpConnection.getHeaderFieldKey(i).equalsIgnoreCase("Content-Length")){
				result=Long.parseLong(httpConnection.getHeaderField(i));
				break;
			}
		}
		return result;
	}

	// 创建指定大小的文件
	private void createFile(String fileName, long fileSize) throws IOException {
		File newFile=new File(fileName);
		RandomAccessFile raf=new RandomAccessFile(newFile,"rw");
		raf.setLength(fileSize);
		raf.close();
	}
}
