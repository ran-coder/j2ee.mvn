package utils.codec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

import utils.StringUtil;

public class EncodeUtil {
	private byte[]	byteKey		=null;
	private static String	algorithm	="DESede";

	private void getKey() {
		if(byteKey == null){
			String path="";
			File file=new File(path);
			int size=(int)file.length();
			byteKey=new byte[size];
			try{
				FileInputStream fin=new FileInputStream(file);
				fin.read(byteKey);//读取明文
				fin.close();
			}catch(FileNotFoundException e){
				//e.printStackTrace();
				System.out.println("File not found:" + path);
			}catch(IOException e){
				//e.printStackTrace();
				System.out.println("File write failure:" + path);
			}
		}
	}

	/**
	 * @param info    要加密或解密的字符串
	 * @param mode    加密(1)还是解密(0) 
	 */
	public String encode(String info, int mode) {
		String rs="";
		if(!StringUtil.isEmpty(info)){
			try{
				// 添加新安全算法,如果用JCE就要把它添加进去
				Security.addProvider(new com.sun.crypto.provider.SunJCE());
				SecretKey secretKey=null;
				// 生成密钥
				if(byteKey == null) getKey();
				secretKey=new javax.crypto.spec.SecretKeySpec(byteKey,algorithm);

				if(secretKey != null){
					Cipher cipher=Cipher.getInstance(algorithm);
					if(mode == 1){ // 加密
						cipher.init(Cipher.ENCRYPT_MODE,secretKey);
						//先DESede 加密 再base64 加密
						byte[] cipherByte=cipher.doFinal(info.getBytes("UTF8"));
						//rs=new BASE64Encoder().encode(cipherByte);
						rs=Base64.encodeBase64String(cipherByte);
					}else{
						cipher.init(Cipher.DECRYPT_MODE,secretKey);
						byte[] clearByte=cipher.doFinal(Base64.decodeBase64(info));
						rs=new String(clearByte,"UTF8");
					}
				}
			}catch(Exception e){
				rs="";
				System.out.println("加解密失败:" + e.getLocalizedMessage());
				//e3.printStackTrace();
			}
		}
		return rs;
	}

	public static String byte2hex(byte[] b) { // 二进制转换16进制
		String hs="";
		String stmp="";
		for(int n=0;n < b.length;n++){
			stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
			if(stmp.length() == 1) hs=hs + "0" + stmp;
			else hs=hs + stmp;
			if(n < b.length - 1) hs=hs + "";// 分割号

		}
		return hs.trim().toUpperCase();
	}

	public static byte[] hex2byte(String h) { // 16进制转换2进制
		h=h.trim();
		byte[] ret=new byte[h.length() / 2];
		for(int i=0;i < ret.length;i++){
			ret[i]=Integer.decode("#" + h.substring(2 * i,2 * i + 2)).byteValue();
		}
		return ret;
	}

	

	public static void main(String[] args) {
		//System.out.println(unicodeToString(toUnicodeMapbar(str,false)));
	}
}
