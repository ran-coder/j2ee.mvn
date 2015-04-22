package j2ee.research.java.collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.Files;

public class BitSetNotInMap {
	final static Charset uft8=Charset.forName("UTF-8");
	Map<Integer,BitSet> all=new HashMap<Integer, BitSet>();
	public void addLeft(int prefix,int postfix){
		//System.out.println(prefix+":"+postfix);
		if(prefix<130||prefix>190)return;
		if(!all.containsKey(prefix))
			all.put(prefix,new BitSet());
		all.get(prefix).set(postfix,true);
		//print();
	}
	public void addRight(int prefix,int postfix){
		if(prefix<130||prefix>190)return;
		if(!all.containsKey(prefix))return;
		//if(all.get(prefix).size()<postfix)return;
		all.get(prefix).set(postfix,false);
	}

	public void addLeft(String mobileStr){
		if(mobileStr==null|| (mobileStr=mobileStr.trim()).length()<1)return;
		if(mobileStr.length()==11){
			addLeft(pauseInt(mobileStr.substring(0,3),0),pauseInt(mobileStr.substring(3),0));
		}else if(mobileStr.length()==10){
			addLeft(pauseInt(mobileStr.substring(0,2),0),pauseInt(mobileStr.substring(2),0));
		}
	}
	public void addRight(String mobileStr){
		if(mobileStr==null|| (mobileStr=mobileStr.trim()).length()<1)return;
		if(mobileStr.length()==11){
			addRight(pauseInt(mobileStr.substring(0,3),0),pauseInt(mobileStr.substring(3),0));
		}else if(mobileStr.length()==10){
			addRight(pauseInt(mobileStr.substring(0,2),0),pauseInt(mobileStr.substring(2),0));
		}
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

	public void writeStringToFile(String path, String encoding){
		/*System.out.println(all.size());
		for(Map.Entry<Integer, BitSet> entry:all.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue().length());
		}*/

		writeByByte(path,encoding);
		//writeByChar(path,encoding);
	}
	StringBuilder builder=new StringBuilder(1024);
	public void bufferPut(FileChannel channel,ByteBuffer buffer,int prefix,int postfix) throws IOException{
		//if(count>100)System.exit(0);count++;
		//System.out.println(prefix+":"+postfix);
		//buffer.reset();
		//buffer.clear();
		buffer.clear();
		//buffer.put(ByteUtil.Number.int2Bytes(prefix));
		//buffer.put(ByteUtil.Number.int2Bytes(postfix));
		//buffer.putInt(prefix);
		//buffer.putInt(postfix);
		builder.setLength(0);
		builder.append(prefix);
		int leng=String.valueOf(postfix).length();
		if(leng<8){
			for(int i=0,len=8-leng;i<len;i++){
				builder.append('0');
			}
		}
		builder.append(postfix).append('\n');
		/*for(char c:builder.toString().toCharArray()){
			buffer.putChar(c);
		}*/
		buffer.put(builder.toString().getBytes(uft8));
		//buffer.put(ByteUtil.Number.int2Bytes(97));
		//buffer.putChar('\n');
		buffer.flip();
		
		channel.write(buffer);
	}

	public void writeByByte(String path, String encoding){
		if(all.size()<1)return;
		FileOutputStream fout = null;
		FileChannel channel = null;
		BitSet bitSet=null;
		int prefix=0;
		File file=null;
		try{
			file=new File(path);
			if(file.delete())Files.touch(file);
		}catch(IOException e){
			e.printStackTrace();
		}
		try {
			fout = new FileOutputStream(path);
			channel = fout.getChannel();
			ByteBuffer buffer=ByteBuffer.allocate(1024);
			for(Map.Entry<Integer, BitSet> entry:all.entrySet()){
				prefix=entry.getKey();
				bitSet=entry.getValue();
				int i = bitSet.nextSetBit(0);
				if (i != -1) {
					System.out.println(entry.getKey()+":"+entry.getValue().length());
					bufferPut(channel,buffer,prefix,i);
					for (i = bitSet.nextSetBit(i+1); i >= 0; i = bitSet.nextSetBit(i+1)) {
						int endOfRun = bitSet.nextClearBit(i);
						do {
							bufferPut(channel,buffer,prefix,i);
						}while (++i < endOfRun);
					}
				}
				bitSet.clear();
				all.put(prefix,null);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(channel!=null)channel.close();
				if(fout!=null)fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void writeByChar(String path, String encoding){
		if(all.size()<1)return;
		BitSet bitSet=null;
		int prefix=0;
		File file=null;
		try{
			file=new File(path);
			if(file.delete())Files.touch(file);
		}catch(IOException e){
			e.printStackTrace();
		}
		StringBuilder builder=new StringBuilder(1024);
		try {
			for(Map.Entry<Integer, BitSet> entry:all.entrySet()){
				System.out.println(entry.getKey()+":"+entry.getValue().length());
				prefix=entry.getKey();
				bitSet=entry.getValue();
				int i = bitSet.nextSetBit(0);
				if (i != -1) {
					writeFixLength(builder,prefix,i,file,uft8);
					for (i = bitSet.nextSetBit(i+1); i >= 0; i = bitSet.nextSetBit(i+1)) {
						int endOfRun = bitSet.nextClearBit(i);
						do {
							writeFixLength(builder,prefix,i,file,uft8);
						}while (++i < endOfRun);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{

		}
	}
	static void writeFixLength(StringBuilder builder,int prefix,int postfix, File file, Charset charset) throws IOException{
		builder.setLength(0);
		builder.append(prefix);
		int leng=String.valueOf(postfix).length();
		if(leng<8){
			for(int i=0,len=8-leng;i<len;i++){
				builder.append('0');
			}
		}
		builder.append(postfix).append('\n');
		Files.append(builder.toString(),file,charset);
	}
	int count=0;
	public void print(){
		//if(count>100)System.exit(0);count++;
		System.out.println(all.size());
		for(Map.Entry<Integer, BitSet> entry:all.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue().length());
		}
	}
}
