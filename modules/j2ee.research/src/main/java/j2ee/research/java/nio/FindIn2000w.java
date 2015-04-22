package j2ee.research.java.nio;

import com.google.common.base.Stopwatch;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.google.common.primitives.Bytes;
import org.apache.commons.io.Charsets;

import java.io.File;
import java.io.IOException;

public class FindIn2000w {
	public static final String PATH="F:/2000W/all.csv";
	public static final String PATH_7Z="F:/2000W/all.csv.7z";
	public static final int CACHE_SIZE=1024*100;//1M

	public static boolean binaryStartsWith(byte[] all,byte[] target){
		//Bytes.indexOf(all,target)
		return Bytes.indexOf(all, target)!=-1;
	}

	public static boolean containsAll(String base,String... strings){
		boolean result=false;
		if(base!=null&&strings!=null&&strings.length>0){
			result=true;
			for(String str:strings){
				result=result&& (base.indexOf(str)!=-1);
			}
		}
		return result;
	}
	public static void main(String[] args) throws IOException {
		Stopwatch watch=Stopwatch.createStarted();
		//InputSupplier<FileInputStream> fileIn=Files.newInputStreamSupplier(new File(PATH));
		//System.out.println(new String(ByteStreams.toByteArray(ByteStreams.slice(fileIn,1073200000L,10000L))));
		/*File all=new File(PATH);
		if(all!=null&&  ( !all.exists() || (all.exists()&&all.delete()) ) ){
			Files.touch(all);
			OutputSupplier<OutputStreamWriter> writer=Files.newWriterSupplier(new File("F:/2000W/all.csv"), Charsets.UTF_8, true);
			Files.copy(new File("F:/2000W/1-200W.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/1000W-1200W.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/1200W-1400W.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/1400W-1600W.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/1600w-1800w.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/1800w-2000w.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/200W-400W.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/400W-600W.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/600W-800W.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/800W-1000W.csv"),Charsets.UTF_8,writer);
			Files.copy(new File("F:/2000W/5000.csv"),Charsets.UTF_8,writer);
		}*/
		/*for(int i=0;i < 100;i++){
			InputSupplier<InputStream> slicedStream=ByteStreams.slice(fileIn,i*10,100);
			byte[] data=ByteStreams.toByteArray(slicedStream);
			System.out.println(new String(data));
			//System.out.println(new String(data));
		}*/
		/*Files.readBytes(file,new ByteProcessor<String>() {
			public boolean processBytes(byte[] buf, int off, int len) throws IOException {
				return false;
			}

			public String getResult() {
				return null;
			}
			
		});*/
		//ByteStreams.readFully(in,b,off,len);
		System.out.println(Files.readFirstLine(new File(PATH),Charsets.UTF_8));
		/**/
		Files.readLines(new File(PATH),Charsets.UTF_8,new LineProcessor<Object>() {
			long count=0L;
			//String keyword="张";
			@Override
			public boolean processLine(String line) throws IOException {
				if(line==null)return false;
				//System.out.println(line.indexOf(keyword));
				if(containsAll(line,"邵丽君,",",F,198")){//,",ID,1306"
					count++;
					System.out.println(line);
				}
				if(count<2000 ){
					return true;
				}
				return false;
			}

			@Override
			public Object getResult() {
				return null;
			}
		});
		//readFile();
		System.out.println(watch.stop().toString());
	}
}
