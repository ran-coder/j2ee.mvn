package j2ee.research.java.nio.basic;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestNio {

	public static void main(String args[]) throws Exception {

		// String infile = "D:\\workspace\\test\\usagetracking.log";
		// FileInputStream fin= new FileInputStream(infile);
		// FileChannel fcin = fin.getChannel();

		int bufSize=100;
		File fin=new File("D:\\workspace\\test\\usagetracking.log");
		File fout=new File("D:\\workspace\\test\\usagetracking2.log");

		FileChannel fcin=new RandomAccessFile(fin,"r").getChannel();
		ByteBuffer rBuffer=ByteBuffer.allocate(bufSize);

		FileChannel fcout=new RandomAccessFile(fout,"rws").getChannel();
		ByteBuffer wBuffer=ByteBuffer.allocateDirect(bufSize);

		readFileByLine(bufSize,fcin,rBuffer,fcout,wBuffer);

		System.out.print("OK!!!");
	}

	public static void readFileByLine(int bufSize, FileChannel fcin, ByteBuffer readBuffer, FileChannel fcout, ByteBuffer writeBuffer)
			throws IOException {
		String enterStr="\n";

		byte[] bs=new byte[bufSize];

		int size=0;
		StringBuffer strBuf=new StringBuffer("");
		// while((size = fcin.read(buffer)) != -1){
		while(fcin.read(readBuffer) != -1){
			int rSize=readBuffer.position();
			readBuffer.rewind();
			readBuffer.get(bs);
			readBuffer.clear();
			String tempString=new String(bs,0,rSize);
			// System.out.print(tempString);
			// System.out.print("<200>");

			int fromIndex=0;
			int endIndex=0;
			while((endIndex=tempString.indexOf(enterStr,fromIndex)) != -1){
				String line=tempString.substring(fromIndex,endIndex);
				line=new String(strBuf.toString() + line);
				// System.out.print(line);
				// System.out.print("</over/>");
				// write to anthone file
				writeFileByLine(fcout,writeBuffer,line);

				strBuf.delete(0,strBuf.length());
				fromIndex=endIndex + 1;
			}
			if(rSize > tempString.length()){
				strBuf.append(tempString.substring(fromIndex,tempString.length()));
			}else{
				strBuf.append(tempString.substring(fromIndex,rSize));
			}
		}

	}

	public static void writeFileByLine(FileChannel fcout, ByteBuffer writeBuffer, String line) {
		try{
			// write on file head
			// fcout.write(wBuffer.wrap(line.getBytes()));
			// wirte append file on foot
			fcout.write(writeBuffer.wrap(line.getBytes()),fcout.size());

		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
