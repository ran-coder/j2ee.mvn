package j2ee.research.os.compress.finder;

import com.google.common.base.Charsets;
import com.google.common.io.LineProcessor;
import com.google.common.primitives.Bytes;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-11-14 14:59
 * To change this template use File | Settings | File Templates.
 */
public class CompressBigFileFinder{
	public static final String PATH="F:/2000W/all.csv";
	public static final String PATH_7Z="F:/2000W/all.7z";
	public static final int CACHE_SIZE=1024*100;//1M

	public static void main(String[] args) throws IOException{
		//InputSupplier<FileInputStream> fileIn=Files.newInputStreamSupplier(new File(PATH));
		//System.out.println(new String(ByteStreams.toByteArray(ByteStreams.slice(fileIn, 0,239)), Charsets.UTF_8));
		//System.out.println(new String(ByteStreams.toByteArray(ByteStreams.slice(fileIn, 239,382)), Charsets.UTF_8));
		//System.out.println(Files.readFirstLine(new File("F:/2000W/all.cvs"), Charsets.UTF_8));

		deCompressBigFile(PATH_7Z, new SearchLineProcessor());
	}

	static void deCompressBigFile(String fileName, LineProcessor lineProcessor) throws IOException{
		SevenZFile sevenZFile=new SevenZFile(new File(fileName));
		SevenZArchiveEntry entry=sevenZFile.getNextEntry();

		int contentLength=1024*10;
		byte[] content;
		int entryLength=(int)entry.getSize()&Integer.MAX_VALUE;
		System.out.println(entry.getName()+":"+entry.getSize()+","+entryLength);

		int offset=0;
		int lineStart=0;
		int lineLength;
		int indexOfLineSeparator;
		boolean isFindLineSeparator;
		while(lineStart<entryLength){
			//System.out.println("lineStart<entryLength:"+lineStart+","+entryLength+":"+(!(lineStart<entryLength)));

			isFindLineSeparator=false;
			lineLength=0;
			offset=lineStart;
			System.out.println("+lineStart-lineLength:"+lineStart+","+lineLength);
			while(!isFindLineSeparator){
				content=new byte[entryLength];
				//offset < 0 || contentLength < 0 || contentLength > b.length - offset
				//false      || false             || 0>- offset
				System.out.println("--+offset,contentLength:"+offset+","+contentLength
						+":"+(offset<0 || contentLength<0 || contentLength>contentLength-offset)         );
				//off < 0 || len < 0 || off + len < 0 || off + len > buf.length
				if(sevenZFile.read(content, offset, contentLength)<1) break;
				offset+=contentLength;
				indexOfLineSeparator=Bytes.indexOf(content, LineSeparator.Unix);
				if(indexOfLineSeparator!=-1){
					lineLength+=indexOfLineSeparator;
					isFindLineSeparator=true;
				}else{
					lineLength+=contentLength;
				}
				System.out.println("---lineStart-lineLength:"+lineStart+","+lineLength);
			}
			content=new byte[lineLength];
			sevenZFile.read(content, lineStart, lineLength);
			lineStart+=lineLength;
			//System.out.println(new String(content, Charsets.UTF_8));
			//true才停止
			//if(lineProcessor.processLine(new String(content, Charsets.UTF_8)))break;
			if(lineProcessor.processLine(new String(content, Charsets.UTF_8)))break;

		}
	}

	static class SearchLineProcessor implements LineProcessor{
		long count=0L;

		@Override
		public boolean processLine(String line) throws IOException{
			if(line==null) return false;
			if(line.indexOf("袁伟")!=-1){
				count++;
				System.out.println(line);
			}
			if(count<10){
				return true;
			}
			//true才停止
			return false;
		}

		@Override
		public Object getResult(){
			return null;
		}
	}
}
