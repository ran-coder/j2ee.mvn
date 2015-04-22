package j2ee.tutorial.google.guava.io;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;

import java.io.InputStream;

public class ChecksumStreamExample{
	public static void main(String[] args) throws Exception {
		InputStream test=ChecksumStreamExample.class.getResourceAsStream("test.data");
		byte[] byteArray=ByteStreams.toByteArray(test);

		long checksum=ByteStreams.hash(ByteStreams.newInputStreamSupplier(byteArray), Hashing.crc32()).asLong();
		System.out.printf("Checksum: %d",checksum);
	}
}
