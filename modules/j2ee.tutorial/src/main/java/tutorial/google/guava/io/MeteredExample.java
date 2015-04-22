package tutorial.google.guava.io;

import java.io.FileInputStream;
import java.io.InputStream;

import com.google.common.io.CountingInputStream;

public class MeteredExample {
	public static final String PATH="E:/iso/rhel-5.2-server-i386-dvd.iso";
	public static void main(String[] args) throws Exception {
		InputStream fis=new FileInputStream(PATH);
		CountingInputStream cis=new CountingInputStream(fis);
		while(cis.read() != -1){
			long bytesRead=cis.getCount();
			if(bytesRead % 50 == 0){
				System.out.printf("Read %d bytes...\n",bytesRead);
			}
		}
		/*OutputStream fos=new FileOutputStream(new File(PathUtil.getFullPathRelateClass(CloseFlushExample.class,"test.data")));
		CountingOutputStream cos=new CountingOutputStream(fos);
		String testString="TEST STRING";
		cos.write(testString.getBytes(Charset.defaultCharset().toString()));
		System.out.printf("Just wrote %d bytes to output.dat",cos.getCount());*/
	}
}
