package utils.codec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: yuanwei
 * Date: 2015-04-09 17:03
 * To change this template use File | Settings | File Templates.
 */
public class EncryptUtil{
	private static final Logger log = LoggerFactory.getLogger(EncryptUtil.class);
	private static final String ALGORITHM_AES = "AES"; // 加密算法AES
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	/**
	 * 加密/解密算法/工作模式/填充方式
	 *
	 * JAVA6 支持PKCS5PADDING填充方式
	 * Bouncy castle支持PKCS7Padding填充方式
	 * */
	public static final String CIPHER_ALGORITHM="AES/ECB/PKCS7Padding";
	private static final byte[] ENCRYPT_ID_KEY = "a3456c3f3ae9874c".getBytes();


	/**
	 * 1.src.getBytes(UTF_8)
	 * 2.aes 加密
	 */
	public static byte[] aesEncrypt(String src,byte[] keyBytes){
		try {
			Cipher encrypt = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM); // 创建密码器
			encrypt.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, ALGORITHM_AES));
			//result=Base64.encodeBase64URLSafeString(encryptBytes);//经过utf8
			////logger.info(String.format("[%s]加密后为[%s],再解密[%s]",id,result,decryptId(result)));
			return encrypt.doFinal(src.getBytes(UTF_8));
		} catch (Exception e){
			log.info(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * aes 解密
	 */
	public static byte[] aesDecrypt(byte[] encrypt,byte[] keyBytes){
		try {
			Cipher decrypt = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM); // 创建密码器
			decrypt.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, ALGORITHM_AES));
			return decrypt.doFinal(encrypt);
		} catch (Exception e){
			log.info(e.getMessage(), e);
			return null;
		}
	}

	public static String encryptId(Long id){
		return encryptId(id,ENCRYPT_ID_KEY);
	}

	/**
	 * 1.src.getBytes(UTF_8)                 String--->byte[]
	 * 2.aes 加密                             byte[]--->byte[]
	 * 3.encodeBase64                        byte[]--->byte[]
	 * 4.newString(bytes, Charsets.UTF_8);   byte[]--->String
	 */
	public static String encryptId(Long id,byte[] keyBytes ){
		if(id!=null&&id<1)return null;

		String src=System.currentTimeMillis()+"#"+id;
		byte[] encryptBytes=aesEncrypt(src,keyBytes);
		return Base64.encodeBase64URLSafeString(encryptBytes);
	}
	/**
	 * 1.src.getBytes(UTF_8)                 String--->byte[]
	 * 2.decodeBase64                        byte[]--->byte[]
	 * 3.aes解密                              byte[]--->byte[]
	 * 4.newString(bytes, Charsets.UTF_8);   byte[]--->String
	 *
	 */
	public static long decryptId(String sid,byte[] keyBytes){
		if(StringUtils.isBlank(sid))return 0;
		byte[] result=Base64.decodeBase64(sid);//经过utf8
		if(result==null)return 0;
		result=aesDecrypt(result,keyBytes);
		if(result==null)return 0;

		String src=new String(result,UTF_8);
		if(org.apache.commons.lang.StringUtils.indexOf(src,'#')==-1)return 0;
		src=org.apache.commons.lang.StringUtils.substringAfterLast(src,"#");
		return org.apache.commons.lang.math.NumberUtils.toLong(src,0);
	}

	public static long decryptId(String sid){
		return decryptId(sid, ENCRYPT_ID_KEY);
	}

	public static void main(String[] args) throws Exception{

		System.out.println(encryptId(1000568L));
		System.out.println(decryptId(encryptId(1000568L)));
		//System.out.println(decryptId("4fIAV7u7Of3wgObtn7Fc9IpgVgnnXq0UxQfcz9t6nUE"));
	}
}
