package j2ee.research.java.collection;

import com.google.common.base.Stopwatch;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-7-26 下午2:54:41
 *   
 */
public class LongIds {
	final static String baseDir="D:/Server/java/sql/";
	final static String ENCODING="UTF-8";
	public static void readFileLine4Header(String path, String charset,BitSet set){
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		//Set<Integer> header=new HashSet<Integer>(99);
		//int count=0;
		try{
			File fl = new File(path);
			fileReader = new FileReader(fl);
			bufferedReader = new BufferedReader(fileReader);
			String currentLine;
			while((currentLine = bufferedReader.readLine())!=null && currentLine.trim().length()>0){
				//System.out.println(currentLine);
				//rs.add(currentLine);
				set(set,currentLine);//count++;
				//set.set(convertMobile2Int(pauseLong(currentLine,0)));
			}	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bufferedReader!=null){
				try{bufferedReader.close();}catch(IOException e){e.printStackTrace();}
			}
			if(fileReader!=null){
				try{fileReader.close();}catch(IOException e){e.printStackTrace();}
			}
		}
		//System.out.println(count);
	}

	public static void writeStringToFile(String path,String data) throws IOException{
		FileUtils.writeStringToFile(new File(path),data,ENCODING);
	}
	public static void set(BitSet set,String str){
		int val=convertMobile2Int(pauseLong(str,0));
		if(val>0)set.set(val);
	}

	public static long pauseLong(String str,long dv){
		long val=dv;
		if(str!=null&& (str=str.trim()).length()==11){
			try{
				val=Long.valueOf(str);
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
		return val;
	}
	public static int convertMobile2Int(long mobile){
		int result=0;
		if(mobile>0){
			long t=mobile/100000000-100;
			if(t>29&&t<99)result=(int)t;
			//if(t==11)throw new RuntimeException(String.valueOf(mobile));
			//if(mobile>100)System.out.println(mobile);
		}
		return result;
	}
	public static void notIn(String fromAll,String notIn) throws IOException{
		final String suffix=".txt";
		//2,147,483,647 Integer.MAX_VALUE
		int leng=2000000;//(int)Math.pow(10,12)
		//leng=(int)Math.pow(10,12);
		BitSet all=new BitSet(leng);
		BitSet notin=new BitSet(leng);

		readFileLine4Header(baseDir+fromAll+suffix,ENCODING,all);
		System.out.println(all.toString());
		readFileLine4Header(baseDir+notIn+suffix,ENCODING,notin);
		System.out.println(notin.toString());

		//FileUtils.write(new File("D:/Server/java/sql/andNot.txt"),data,encoding)
		//FileUtils.writeStringToFile(new File("D:/Server/java/sql/andNot.txt"),ByteUtil.toBinaryString(all.toLongArray()),ENCODING);
		//all.andNot(notin);
		all.and(notin);
		System.out.println(all.toString());

		//FileUtils.writeStringToFile(new File(baseDir+"result_"+fromAll+"_"+notIn+suffix),all.toString(),ENCODING);
		//ClassUtil.sleep(1000000);
		//System.out.println(all);
	}
	public static void main(String[] args) throws IOException {
		Stopwatch watch=Stopwatch.createStarted();

		//ClassUtil.sleep(30000L);
		/**/
		notIn("mobile-all","mobile-not");//17933 notIn 29134 =14058
		System.out.println(watch.stop().toString());
		//ClassUtil.sleep(60000L);

		//long num=15868020959L;
		//System.out.println(num/100000000);
		//System.out.println(num/100000000-100);
		//System.out.println(convertMobile2Int(num));
		//System.out.println(convertMobile2Int(18666000998L));
	}
	
}
