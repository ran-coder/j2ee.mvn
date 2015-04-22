package tutorial.apache.util.codec;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.digest.DigestUtils;

public class DigestUtilsTest {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(DigestUtils.md5Hex("111111"));
		System.out.println( Base64.encodeBase64String("96e79218965eb72c92a549dd5a330112".getBytes()) );
		System.out.println( new String(Base64.decodeBase64("YWRtaW46MTI5MzQxODg5MzY0MDplMDM5NGEwZGE1MDY2ZmE2MDgyMzhiZTVlNzcwZDFmZg==")) );
		System.out.println(BinaryCodec.toAsciiString(new String("中文").getBytes("UTF-8")));

	}

}
