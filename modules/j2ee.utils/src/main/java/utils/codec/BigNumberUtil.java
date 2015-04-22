package utils.codec;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-9-1 下午3:24:26
 *   
 */
public class BigNumberUtil {
	/** 数字字符串转成byte数组 */
	public static byte[] numberString2Bytes(String bigNumber,int radix) {
		return new BigInteger(bigNumber,radix).toByteArray();
	}
	public static byte[] numberString2Bytes(String bigNumber){
		return numberString2Bytes(bigNumber,10);
	}
	public static byte[] binaryNumberString2Bytes(String bigNumber){
		return numberString2Bytes(bigNumber,2);
	}
	public static byte[] hexNumberString2Bytes(String bigNumber){
		return numberString2Bytes(bigNumber,16);
	}
	public static String bytes2NumberString(byte[] bytes,int radix) {
		return new BigInteger(bytes).toString(radix);
	}
	public static void main(String[] args) {
		BigInteger big=new BigInteger("1546541454656456454545",10);
		System.out.println("*************** big:1546541454656456454545 ***************");
		System.out.println(big.toString(36)+":36");
		System.out.println(big.toString(16)+":16");
		byte[] bytes=big.toByteArray();
		System.out.println(ByteUtil.toBinaryString(bytes));
		System.out.println(Base64.encodeBase64URLSafeString(bytes)+":64");

		System.out.println("*************** big:dsadsad464d6sd45w2e45a4s6d5saqw1223daqw ***************");
		big=new BigInteger("dsadsad464d6sd45w2e45a4s6d5saqw1223daqw",36);
		System.out.println(big.toString(16)+":16");
		bytes=big.toByteArray();
		System.out.println(ByteUtil.toBinaryString(bytes));
		System.out.println(Base64.encodeBase64URLSafeString(bytes)+":64");

		System.out.println("*************** big:dsadsad4 ***************");
		big=new BigInteger("dsadsad4",36);
		System.out.println(big.longValue());
	}
}
