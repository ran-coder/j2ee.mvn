package j2ee.search.lucene.example.e1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author yuanwei
 * @version ctreateTime:2012-5-16 下午3:18:51 http://cumtfirefly.iteye.com/blog/543664
 */
public class FilePreprocess {
	/** 索引存放目录 */
	public final static String	INDEX_DIR			="D:/Server/java/search/index/eming/";
	/** 小文件存放的目录 */
	public final static String	DATA_DIR			="D:/Server/java/search/small/eming/";
	/** 小文件存放路径 */
	public final static String	OUTPUTPATH			="D:/Server/java/search/small/eming/";
	/** 原文件存放路径 */
	public final static String	DATA_SOURCE_PATH	="D:/Server/java/search/big/恶明.txt";
	
	public static void main(String[] arg) {
		if(!new File(OUTPUTPATH).exists()){
			new File(OUTPUTPATH).mkdirs();
		}
		splitToSmallFiles(new File(DATA_SOURCE_PATH),OUTPUTPATH);
	}
	
	/**
	 * 大文件切割为小的
	 * @param file
	 * @param outputpath
	 */
	public static void splitToSmallFiles(File file, String outputpath) {
		int filePointer=0;
		int MAX_SIZE=10240;
		String filename="output";
		
		BufferedWriter writer=null;
		try{
			BufferedReader reader=new BufferedReader(new FileReader(file));
			StringBuffer buffer=new StringBuffer();
			String line=reader.readLine();
			while(line != null){
				buffer.append(line).append("\r\n");
				if(buffer.toString().getBytes().length >= MAX_SIZE){
					writer=new BufferedWriter(new FileWriter(outputpath + filename + filePointer + ".txt"));
					writer.write(buffer.toString());
					writer.close();
					filePointer++;
					buffer=new StringBuffer();
				}
				line=reader.readLine();
			}
			writer=new BufferedWriter(new FileWriter(outputpath + filename + filePointer + ".txt"));
			writer.write(buffer.toString());
			writer.close();
			System.out.println("The file hava splited to small files !");
		}catch(FileNotFoundException e){
			System.out.println("file not found !");
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}