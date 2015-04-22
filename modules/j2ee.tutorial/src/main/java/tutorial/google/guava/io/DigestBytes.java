package tutorial.google.guava.io;

import java.io.InputStream;
import java.security.MessageDigest;
import com.google.common.io.ByteStreams;

public class DigestBytes {
	public static void main(String[] args) throws Exception {
		InputStream test=DigestBytes.class.getResourceAsStream("test.data");
		byte[] byteArray=ByteStreams.toByteArray(test);
		MessageDigest sha=MessageDigest.getInstance("SHA-256");
		byte[] digest=ByteStreams.getDigest(ByteStreams.newInputStreamSupplier(byteArray),sha);
		for(byte b:digest)
			System.out.printf("%02x:",0xFF & b);
	}
}
