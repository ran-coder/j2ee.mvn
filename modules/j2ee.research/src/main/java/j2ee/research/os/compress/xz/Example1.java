package j2ee.research.os.compress.xz;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.UnsupportedOptionsException;
import org.tukaani.xz.XZOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-11-12 16:37
 * To change this template use File | Settings | File Templates.
 */
public class Example1{
	public static void main(String[] args){
		FileInputStream inFile = null;
		FileOutputStream outfile = null;

		try{
			inFile = new FileInputStream("src.tar");
			outfile = new FileOutputStream("src.tar.xz");
			LZMA2Options options = new LZMA2Options();
			options.setPreset(7); // play with this number: 6 is default but 7 works better for mid sized archives ( > 8mb)

			XZOutputStream out = new XZOutputStream(outfile, options);

			byte[] buf = new byte[8192];
			int size;
			while ((size = inFile.read(buf)) != -1)
				out.write(buf, 0, size);
			out.finish();
		}catch(UnsupportedOptionsException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(inFile != null){
				try{inFile.close();}catch(IOException e){e.printStackTrace();}
			}
			if(outfile != null){
				try{outfile.close();}catch(IOException e){e.printStackTrace();}
			}
		}
	}
}
