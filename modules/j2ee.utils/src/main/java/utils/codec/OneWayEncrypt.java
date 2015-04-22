package utils.codec;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public abstract class OneWayEncrypt {
	public static final String	KEY_SHA	="SHA";
	public static final String	KEY_MD5	="MD5";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String	KEY_MAC	="HmacMD5";
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decodeBase64(key);  
	}
	public static String encryptBASE64(byte[] binaryData) throws Exception {
		return Base64.encodeBase64URLSafeString(binaryData);  
	}
	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5=MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha=MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey=keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey=new SecretKeySpec(decryptBASE64(key),KEY_MAC);
		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}
	
	public static void main(String[] args) throws Exception {
		String inputStr = "简单加密";
		System.err.println("原文:" + inputStr);

		byte[] inputData = inputStr.getBytes();
		String code = encryptBASE64(inputData);

		System.err.println("BASE64加密后:" + code);

		byte[] output = decryptBASE64(code);

		String outputStr = new String(output);

		System.err.println("BASE64解密后:" + outputStr);

		String key = initMacKey();
		System.err.println("Mac密钥:" + key);

		BigInteger md5 = new BigInteger(encryptMD5(inputData));
		System.err.println("MD5:" + md5.toString(16));

		BigInteger sha = new BigInteger(encryptSHA(inputData));
		System.err.println("SHA:" + sha.toString(32));

		BigInteger mac = new BigInteger(encryptHMAC(inputData, inputStr));
		System.err.println("HMAC:" + mac.toString(16));
	}
}
