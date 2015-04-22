package j2ee.tutorial.google.guava.io;

import com.google.common.io.*;
import utils.io.PathUtil;

import java.io.*;

public class CloseFlushExample {
	public static void main(String[] args) {
		OutputStream out=null;
		try{
			out=new FileOutputStream(new File(PathUtil.getFullPathRelateClass("test.data",CloseFlushExample.class)));
			// Do something fantastic with this file!!!
			// etc.
			byte magnificentByte=1;
			out.write(magnificentByte);
		}catch(FileNotFoundException fnfe){
			// Do something about this file not being found.
		}catch(IOException ioe){
			// Egad, there's been an exception! Do something!!!
		}finally{
			//Flushables.flushQuietly(out);
			//Closeables.closeQuietly(out);
			//try{out.close();}catch(IOException e){e.printStackTrace();}

			try{
				if(out != null){
					out.flush();
					out.close();
				}
			}catch(IOException e){e.printStackTrace();}
		}
		OutputSupplier<FileOutputStream> os ;
		byte magnificentByte=1;
		try{
			os = Files.newOutputStreamSupplier(new File(PathUtil.getFullPathRelateClass("test.data",CloseFlushExample.class)));
			ByteStreams.write(new byte[]{ magnificentByte },os);
		}catch(IOException e){
			// Problem writing to file.
		}

	}
}
