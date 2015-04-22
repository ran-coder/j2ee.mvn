package tutorial.google.guava.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import utils.io.PathUtil;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.Flushables;
import com.google.common.io.OutputSupplier;

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
			Flushables.flushQuietly(out);
			Closeables.closeQuietly(out);

			/*try{
				if(os != null){
					os.flush();
					os.close();
				}
			}catch(IOException e){
				// Ok, what are you going to do now?
			}*/
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
