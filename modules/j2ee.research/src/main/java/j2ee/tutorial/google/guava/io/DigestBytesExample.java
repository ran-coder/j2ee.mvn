package j2ee.tutorial.google.guava.io;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.apache.commons.codec.binary.Base64;

import java.io.File;

public class DigestBytesExample{
	public final static String PATH="D:\\Server\\IDE\\idea\\IDEA12\\IdeaProjects\\j2ee.mvn\\modules\\j2ee.research\\src\\main\\java\\";
	public static void main(String[] args) throws Exception {
		byte[] digest;
		//DigestBytesExample.class.getResource("test.data");
		//InputStream test=DigestBytesExample.class.getResourceAsStream("test.data");
		///byte[] byteArray=ByteStreams.toByteArray(DigestBytesExample.class.getResourceAsStream("test.data"));
		//System.out.println(new String(byteArray));
		//byte[] digest=ByteStreams.getDigest(ByteStreams.newInputStreamSupplier(byteArray),MessageDigest.getInstance("SHA-256"));
		//InputSupplier<ByteArrayInputStream> supplier=ByteStreams.newInputStreamSupplier(byteArray);
		//InputSupplier<FileInputStream> inputSupplier= Files.newInputStreamSupplier(new File("./test.data"));
		//digest=ByteStreams.hash(inputSupplier, Hashing.sha256()).asBytes();


		/*System.out.println(new File(".").getAbsolutePath());
		System.out.println(new File("/").getAbsolutePath());
		System.out.println(new File("./").getAbsolutePath());*/
		System.out.println(DigestBytesExample.class.getResource(".").toString());
		System.out.println(DigestBytesExample.class.getResource("/").toString());
		System.out.println(DigestBytesExample.class.getResource("./").toString());
		System.out.println(DigestBytesExample.class.getResource("DigestBytesExample.class").toString());
		digest=Files.hash(new File(PATH+"j2ee\\tutorial\\google\\guava\\io\\test.data"), Hashing.sha256()).asBytes();
		System.out.println(Base64.encodeBase64URLSafeString(digest));

		System.out.println(Base64.encodeBase64URLSafeString(  ByteSource.wrap("1234".getBytes()).hash(Hashing.sha1()).asBytes()  ));
		System.out.println(Base64.encodeBase64URLSafeString(  ByteSource.wrap("1234".getBytes()).hash(Hashing.sha256()).asBytes()  ));
		System.out.println(Base64.encodeBase64URLSafeString(  ByteSource.wrap("1234".getBytes()).hash(Hashing.sha512()).asBytes()  ));

	}
}
