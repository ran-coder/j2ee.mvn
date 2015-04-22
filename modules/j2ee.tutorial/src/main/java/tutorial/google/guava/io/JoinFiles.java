package tutorial.google.guava.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;

public class JoinFiles {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		InputSupplier<FileInputStream> is1=Files.newInputStreamSupplier(new File("test1.txt"));
		InputSupplier<FileInputStream> is2=Files.newInputStreamSupplier(new File("test2.txt"));
		InputSupplier<InputStream> combined=ByteStreams.join(is1,is2);
		OutputSupplier<FileOutputStream> os=Files.newOutputStreamSupplier(new File("output1.data"),false);
		ByteStreams.copy(combined,os);
	}
}
