package j2ee.research.java.collection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;

import org.apache.commons.io.FileUtils;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-7-26 下午2:54:41
 *   
 */
public class CustomerUtil {
	final static String baseDir="D:/Server/java/sql/";
	final static String ENCODING="UTF-8";
	public static void readFileLine(String path, String charset,BitSet set){
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
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
		int val=pauseInt(str,0);
		if(val>0)set.set(val);
	}
	public static int pauseInt(String str,int dv){
		int val=dv;
		if(str!=null&& (str=str.trim()).length()>0){
			try{
				val=Integer.valueOf(str);
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
		return val;
	}
	public static long pauseLong(String str,long dv){
		long val=dv;
		if(str!=null&& (str=str.trim()).length()>0){
			try{
				val=Long.valueOf(str);
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
		return val;
	}
	public static void notIn(String fromAll,String notIn,String result) throws IOException{
		//2,147,483,647 Integer.MAX_VALUE
		int leng=737864;//(int)Math.pow(10,12)
		//leng=(int)Math.pow(10,12);
		BitSet all=new BitSet(leng);
		BitSet notin=new BitSet(leng);

		readFileLine(baseDir+fromAll,ENCODING,all);
		readFileLine(baseDir+notIn,ENCODING,notin);
		System.out.println(all.length());
		System.out.println(notin.length());
		
		//writeStringToFile("D:/Server/java/sql/allBytes.txt", ByteUtil.toBinaryString(ByteUtil.Number.toBytes(all.toLongArray()))    );
		//writeStringToFile("D:/Server/java/sql/notinBytes.txt", ByteUtil.toBinaryString(ByteUtil.Number.toBytes(notin.toLongArray()))    );

		//FileUtils.write(new File("D:/Server/java/sql/andNot.txt"),data,encoding)
		//FileUtils.writeStringToFile(new File("D:/Server/java/sql/andNot.txt"),ByteUtil.toBinaryString(all.toLongArray()),ENCODING);
		all.andNot(notin);
		//writeStringToFile("D:/Server/java/sql/andNotBytes.txt", ByteUtil.toBinaryString(ByteUtil.Number.toBytes(all.toLongArray()))    );
		//FileUtils.writeStringToFile(new File("D:/Server/java/sql/andNotLong.txt"),Arrays.toString(all.toLongArray()),ENCODING);
		//FileUtils.writeStringToFile(new File("D:/Server/java/sql/andNotByteArray.txt"),ByteUtil.toBinaryString(all.toByteArray()),ENCODING);
		FileUtils.writeStringToFile(new File(baseDir+"result_"+result),all.toString(),ENCODING);
		//ClassUtil.sleep(1000000);
		//System.out.println(all);
	}
	public static void notIn(String fromAll,String notIn) throws IOException{
		final String suffix=".txt";
		//2,147,483,647 Integer.MAX_VALUE
		int leng=737864;//(int)Math.pow(10,12)
		//leng=(int)Math.pow(10,12);
		BitSet all=new BitSet(leng);
		BitSet notin=new BitSet(leng);

		readFileLine(baseDir+fromAll+suffix,ENCODING,all);
		System.out.println(all.length());
		readFileLine(baseDir+notIn+suffix,ENCODING,notin);
		System.out.println(notin.length());
		
		//writeStringToFile("D:/Server/java/sql/allBytes.txt", ByteUtil.toBinaryString(ByteUtil.Number.toBytes(all.toLongArray()))    );
		//writeStringToFile("D:/Server/java/sql/notinBytes.txt", ByteUtil.toBinaryString(ByteUtil.Number.toBytes(notin.toLongArray()))    );

		//FileUtils.write(new File("D:/Server/java/sql/andNot.txt"),data,encoding)
		//FileUtils.writeStringToFile(new File("D:/Server/java/sql/andNot.txt"),ByteUtil.toBinaryString(all.toLongArray()),ENCODING);
		all.andNot(notin);
		//writeStringToFile("D:/Server/java/sql/andNotBytes.txt", ByteUtil.toBinaryString(ByteUtil.Number.toBytes(all.toLongArray()))    );
		//FileUtils.writeStringToFile(new File("D:/Server/java/sql/andNotLong.txt"),Arrays.toString(all.toLongArray()),ENCODING);
		//FileUtils.writeStringToFile(new File("D:/Server/java/sql/andNotByteArray.txt"),ByteUtil.toBinaryString(all.toByteArray()),ENCODING);
		FileUtils.writeStringToFile(new File(baseDir+"result_"+fromAll+"_"+notIn+suffix),all.toString(),ENCODING);
		//ClassUtil.sleep(1000000);
		//System.out.println(all);
	}
	public static void main(String[] args) throws IOException {
		long start=System.currentTimeMillis();
/*		notIn("saletracelog_all_customerid.txt","notInLast.txt","result_saletracelog_all_customerid_notInLast.txt");
		System.out.println(System.currentTimeMillis()-start);

		start=System.currentTimeMillis();
		notIn("all.txt","notin.txt","all_notin.txt");
		System.out.println(System.currentTimeMillis()-start);

		start=System.currentTimeMillis();
		notIn("customer2009-2010.txt","customer2010-2012.txt","customer2009-2010l_notin.txt");//17933 notIn 29134 =14058
		System.out.println(System.currentTimeMillis()-start);
		
		start=System.currentTimeMillis();
		notIn("order11175.txt","order10894.txt","order11175_notin.txt");//17933 notIn 29134 =14058
		System.out.println(System.currentTimeMillis()-start);*/
		//ClassUtil.sleep(30000L);
		//start=System.currentTimeMillis();
		//notIn("mobile-all","mobile-not");//17933 notIn 29134 =14058
		//System.out.println(System.currentTimeMillis()-start);
		//ClassUtil.sleep(10000000L);
		System.out.println(pauseInt("13001006028",0));
	}
	
}
