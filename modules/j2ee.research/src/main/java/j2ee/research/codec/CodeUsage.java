package j2ee.research.codec;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-17 09:37
 * To change this template use File | Settings | File Templates.
 */
public class CodeUsage{
	public static String base32(byte[] binaryData){
		//encodeBase64(binaryData, isChunked, urlSafe, Integer.MAX_VALUE);
		//new Base64(0, CHUNK_SEPARATOR, urlSafe)
		//StringUtils.newStringUtf8(encodeBase64(binaryData, false, true))
		//System.out.println(new Base32(0, null,true).encodeAsString(binaryData));
		//System.out.println(new Base32(0, null,false).encodeAsString(binaryData));
		return new Base32(0, null,false).encodeAsString(binaryData);
	}
	public static void print(String name,byte[] bytes){
		System.out.println("****************** "+name+" ******************");
		String code=Hex.encodeHexString(bytes).toLowerCase();System.out.println(code+":hex:"+code.length());
		code=base32(bytes).toLowerCase();System.out.println(code+":base32:"+code.length());
		code=Base64.encodeBase64URLSafeString(bytes).toLowerCase();System.out.println(code+":base64:"+code.length());
	}
	public static void print(byte[] bytes){

	}
	public static void main(String[] args){
		String card="121234561234";//12 0xxxxx xxxx 12 1xxxxx xxxx
		byte[] bytes=DigestUtils.md5(card);print("md5",bytes);
		bytes=DigestUtils.sha(card);print("sha",bytes);


	}
}
