package utils.codec.rsa;

import java.io.*;
import java.security.*;

/**
 * <p>Title: RSA非对称型加密的公钥和私钥</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class KeyRSA {


	/**
	 * 构造函数
	 * @param in 指定密匙长度（取值范围：512～2048）
	 * @throws NoSuchAlgorithmException 异常
	 */
	public KeyRSA(int in, String address) throws Exception {
		KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA"); //创建‘密匙对’生成器
		keyPairGenerator.initialize(in); //指定密匙长度（取值范围：512～2048）
		KeyPair keyPair=keyPairGenerator.genKeyPair(); //生成‘密匙对’，其中包含着一个公匙和一个私匙的信息
		PublicKey publicKey=keyPair.getPublic(); //获得公匙
		PrivateKey privateKey=keyPair.getPrivate(); //获得私匙
		//保存公匙
		FileOutputStream public_file_out=new FileOutputStream(address + "/public_key.dat");
		ObjectOutputStream public_object_out=new ObjectOutputStream(public_file_out);
		public_object_out.writeObject(publicKey);
		//保存私匙
		FileOutputStream private_file_out=new FileOutputStream(address + "/private_key.dat");

		ObjectOutputStream private_object_out=new ObjectOutputStream(private_file_out);
		private_object_out.writeObject(privateKey);
	}

	/**
	 * 构造函数
	 * @param in 指定密匙长度（取值范围：512～2048）
	 * @throws NoSuchAlgorithmException 异常
	 */
	public KeyRSA(int in) throws Exception {
		KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA"); //创建‘密匙对’生成器
		keyPairGenerator.initialize(in); //指定密匙长度（取值范围：512～2048）
		KeyPair keyPair=keyPairGenerator.genKeyPair(); //生成‘密匙对’，其中包含着一个公匙和一个私匙的信息
		PublicKey publicKey=keyPair.getPublic(); //获得公匙
		PrivateKey privateKey=keyPair.getPrivate(); //获得私匙
		System.out.println(publicKey+"\n"+privateKey);
	}
	
	public static void main(String[] args) throws Exception {
			//System.out.println("私匙和公匙保存到C盘下的文件中.");
			new KeyRSA(1024);
	}

}