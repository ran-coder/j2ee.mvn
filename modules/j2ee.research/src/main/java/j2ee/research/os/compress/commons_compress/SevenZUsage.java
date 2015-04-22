package j2ee.research.os.compress.commons_compress;

import j2ee.research.os.compress.Compress;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZMethod;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-11-12 17:22
 * To change this template use File | Settings | File Templates.
 */
public class SevenZUsage{
	static void compress(String outputFileName,String password) throws IOException{
		SevenZOutputFile sevenZOutput = new SevenZOutputFile(new File(Compress.BASE_DIR+outputFileName));
		sevenZOutput.setContentCompression(SevenZMethod.AES256SHA256);

		SevenZArchiveEntry entry1 = sevenZOutput.createArchiveEntry(new File(Compress.BASE_DIR+"./customer1.txt"), "customer1.txt");
		SevenZArchiveEntry entry2 = sevenZOutput.createArchiveEntry(new File(Compress.BASE_DIR+"customer2.txt"), "customer2.txt");
		sevenZOutput.putArchiveEntry(entry1);sevenZOutput.write("customer1.txt".getBytes());sevenZOutput.closeArchiveEntry();
		sevenZOutput.putArchiveEntry(entry2);sevenZOutput.write("customer2.txt".getBytes());sevenZOutput.closeArchiveEntry();
		//sevenZOutput.write("entry1".getBytes()); //附加数据
		//sevenZOutput.finish();//写入
		//sevenZOutput.closeArchiveEntry();
		sevenZOutput.close();
	}
	static void compress(String outputFileName) throws IOException{
		SevenZOutputFile sevenZOutput = new SevenZOutputFile(new File(outputFileName));
		sevenZOutput.setContentCompression(SevenZMethod.LZMA2);
		//sevenZOutput.setContentCompression(SevenZMethod.AES256SHA256);
		SevenZArchiveEntry entry1 = sevenZOutput.createArchiveEntry(new File(Compress.BASE_DIR+"./customer1.txt"), "customer1.txt");
		SevenZArchiveEntry entry2 = sevenZOutput.createArchiveEntry(new File(Compress.BASE_DIR+"customer2.txt"), "customer2.txt");
		sevenZOutput.putArchiveEntry(entry1);sevenZOutput.write("customer1.txt".getBytes());sevenZOutput.closeArchiveEntry();
		sevenZOutput.putArchiveEntry(entry2);sevenZOutput.write("customer2.txt".getBytes());sevenZOutput.closeArchiveEntry();
		//sevenZOutput.write("entry1".getBytes()); //附加数据
		//sevenZOutput.finish();//写入
		//sevenZOutput.closeArchiveEntry();
		sevenZOutput.close();
	}
	static void deCompress(String fileName) throws IOException{
		SevenZFile sevenZFile = new SevenZFile(new File(fileName));
		/*SevenZArchiveEntry entry = sevenZFile.getNextEntry();
		int contentLength=1000;
		byte[] content = new byte[contentLength];
		long offset=-1;
		long entryLength=entry.getSize();
		while(offset<entryLength){
			sevenZFile.read(content);
			offset+=contentLength;
		}*/
		SevenZArchiveEntry entry =null;
		while(true){
			entry = sevenZFile.getNextEntry();
			if(entry==null)break;
			System.out.println(entry.getName()+":"+entry.getSize());
		}
	}


	public static void main(String[] args) throws IOException{
		//String outputFileName=Compress.BASE_DIR+"./7z/SevenZUsage-compress.7z";
		//compress(outputFileName);deCompress(outputFileName);
		//deCompress("F:/2000W/all.7z");
		//create7ZFile("./7z/SevenZUsage-create7ZFile.7z");
		//deCompress("./7z/SevenZUsage-create7ZFile.7z");
		//compress();
		//deCompress("./7z/SevenZUsage.7z");

	}
}
