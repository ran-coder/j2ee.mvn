package utils.codec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

import utils.StringUtil;
public class DesEncode {
	private byte[] byteKey=null;
	private String algorithm = "DESede";// 定义加密算法,可用 DES,DESede,Blowfish
	
	public DesEncode() {
		algorithm="DESede";
	}
	
	public DesEncode(String algorithm) {
		if(StringUtil.isEmpty(algorithm))algorithm="DESede";
		this.algorithm=algorithm;
	}
	public DesEncode(String algorithm,String secretKeyString) {
		this(algorithm);
		if(!StringUtil.isEmpty(secretKeyString))
			this.byteKey = hex2byte(secretKeyString);
	}
	
	/**
	 * @param info 				要加密或解密的字符串
	 * @param secretKeyString  密匙
	 * @param mode  			加密(1)还是解密(0) 
	 *  @see      http://www.cnblogs.com/Mozier/articles/159828.html
	 */
	public String desHexEncode(String info, String secretKeyString, int mode) {
		String rs = "";
		if (!StringUtil.isEmpty(info)) {
			// 添加新安全算法,如果用JCE就要把它添加进去
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			try {
				SecretKey secretKey = null;
				// 生成密钥
				if (!StringUtil.isEmpty(secretKeyString)) {
					byte[] desEncode = hex2byte(secretKeyString);
					secretKey = new javax.crypto.spec.SecretKeySpec(desEncode, algorithm);
				}
				if (secretKey != null) {
					Cipher cipher = Cipher.getInstance(algorithm);
					if (mode == 1) { // 加密
						cipher.init(Cipher.ENCRYPT_MODE, secretKey);
						byte[] cipherByte = cipher.doFinal(info.getBytes("UTF8"));
						rs = byte2hex(cipherByte);
					} else {
						cipher.init(Cipher.DECRYPT_MODE, secretKey);
						byte[] clearByte = cipher.doFinal(hex2byte(info));
						rs = new String(clearByte, "UTF8");
					}
				}
			} catch (java.security.NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} catch (javax.crypto.NoSuchPaddingException e2) {
				e2.printStackTrace();
			} catch (java.lang.Exception e3) {
				e3.printStackTrace();
			}
		}
		return rs;
	}
	
	public SecretKey getSecretKey(String SecretKeyString) {
		SecretKey secretKey = null;
		try {
			/*
			 * String algorithm="DES"; KeyGenerator keygen =
			 * KeyGenerator.getInstance(algorithm); SecretKey deskey =
			 * keygen.generateKey(); byte[] desEncode=deskey.getEncoded();
			 * javax.crypto.spec.SecretKeySpec destmp=new
			 * javax.crypto.spec.SecretKeySpec(desEncode,algorithm); SecretKey
			 * mydeskey=destmp;
			 */
			byte[] desEncode = hex2byte(SecretKeyString);
			secretKey = new javax.crypto.spec.SecretKeySpec(desEncode, algorithm);
		} catch (Exception e) {
		}

		return secretKey;
	}

	public String getSecretKeyString(SecretKey secretKey) {
		String rs = "";
		try {
			byte[] desEncode = secretKey.getEncoded();
			rs = byte2hex(desEncode);
		} catch (Exception e) {
		}
		return rs;
	}

	public String getNewSecretKeyString(String algorithm) {
		String rs = "";
		try {
			KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
			SecretKey deskey = keygen.generateKey();
			byte[] desEncode = deskey.getEncoded();
			rs = byte2hex(desEncode);
		} catch (Exception e) {
		}
		return rs;
	}

	public String byte2hex(byte[] b) { // 二进制转换16进制
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "";// 分割号
		}
		return hs.trim().toUpperCase();
	}

	public byte[] hex2byte(String h) { // 16进制转换2进制
		h=h.trim();
		byte[] ret = new byte[h.length() / 2];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = Integer.decode("#" + h.substring(2 * i, 2 * i + 2)).byteValue();
		}
		return ret;
	}

	public static String base64(String str, int mode) {
		if(str==null)return null;
		String rs = null;

		if (mode == 1) {
			try{
				rs=Base64.encodeBase64String(str.trim().getBytes("UTF8"));
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		} else {
			try{
				rs = new String(Base64.decodeBase64(str), "UTF8");
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}

		return rs;
	}
	
	public void setKey(String path) {
		if(byteKey==null) {
			File file = new File(path);
			int size = (int) file.length();
			this.byteKey = new byte[size];
			try {
				FileInputStream fin = new FileInputStream(file);
				fin.read(byteKey);//读取明文
				fin.close();
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
				System.out.println("File not found:"+path);
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("File write failure:"+path);
			}
		}
	}

	public  String encodeWithBase64(String info,int mode) {
		String rs = "";
		if (!StringUtil.isEmpty(info)) {
			try {
				// 添加新安全算法,如果用JCE就要把它添加进去
				
				Security.addProvider(new com.sun.crypto.provider.SunJCE());
				SecretKey secretKey = null;
				// 生成密钥
				//if(byteKey==null)getKey();
				//System.out.println(byte2hex(this.byteKey));
				secretKey = new javax.crypto.spec.SecretKeySpec(this.byteKey, algorithm);
				
				if (secretKey != null) {
					Cipher cipher = Cipher.getInstance(algorithm);
					if (mode == 1) { // 加密
						cipher.init(Cipher.ENCRYPT_MODE, secretKey);
						//先DESede 加密 再base64 加密
						byte[] cipherByte = cipher.doFinal(info.getBytes("UTF8"));
						//rs=new BASE64Encoder().encode(cipherByte);
						rs=Base64.encodeBase64String(cipherByte);
					} else {
						cipher.init(Cipher.DECRYPT_MODE, secretKey);
						byte[] clearByte = cipher.doFinal(Base64.decodeBase64(info));
						rs = new String(clearByte, "UTF8");
					}
				}
			} catch (Exception e) {
				rs="";
				System.out.println("加解密失败:"+e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		return rs;
	}
	
	public static void main(String[] args) {
		String secretKeyString = "B3378FCE168FEF5467A44AAB26B5737C2637452C0EC467BF";
		String info="http://topic.csdn.net/u/20080419/15/16285952-8532-4214-b8d5-56f8b8395b28.html";
		//String str="To04/rCpCHYe2C3PmrvN+gxdKpoCuq4etWFy0cT7v/WnDV7FpFuzRkiKc9KU0JY7YpixbH5ejvB+vCKCotZIFQEEF1OUA210dN2BHv/gXQE=";
		
		DesEncode des=new DesEncode("",secretKeyString);
		System.out.println(des.desHexEncode(info, secretKeyString, 1));
		System.out.println(des.desHexEncode(des.desHexEncode(info, secretKeyString, 1), secretKeyString, 0));
		//System.out.println(des.encodeWithBase64(info, 1));
		//System.out.println(des.encodeWithBase64(str, 2));

	}
}
