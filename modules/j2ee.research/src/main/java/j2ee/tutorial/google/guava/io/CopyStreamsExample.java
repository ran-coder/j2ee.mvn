package j2ee.tutorial.google.guava.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.google.common.io.ByteStreams;

public class CopyStreamsExample{
	public static void main(String[] args) throws IOException {
		InputStream is=CopyStreamsExample.class.getResourceAsStream("test.data");
		OutputStream os=System.out;
		ByteStreams.copy(is,os);
	}
}
