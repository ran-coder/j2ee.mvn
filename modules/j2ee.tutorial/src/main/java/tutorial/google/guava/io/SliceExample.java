package tutorial.google.guava.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

public class SliceExample {
	//PathUtil.getFullPathRelateClass("large.txt",SliceExample.class)
	//E:/iso/rhel-5.2-server-i386-dvd.iso
	//path="D:/Server/lib/database/MySQL/mysql-connector-5.1.7-bin.jar";
	public static final String PATH="D:/Server/IDE/MyEclipse6.6/flt/J2EE-MVN/activemq-data/localhost/KahaDB/db-1.log";
	public static void main(String[] args) throws IOException {
		InputSupplier<FileInputStream> fileIn=Files.newInputStreamSupplier(new File(PATH));
		for(int i=0;i < 100;i++){
			InputSupplier<InputStream> slicedStream=ByteStreams.slice(fileIn,i*10,10);
			byte[] data=ByteStreams.toByteArray(slicedStream);
			System.out.println(java.util.Arrays.toString(data));
			//System.out.println(new String(data));
		}
	
		
	}
}
