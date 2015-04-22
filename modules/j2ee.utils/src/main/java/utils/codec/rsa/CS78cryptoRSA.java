package utils.codec.rsa;
/*************************************************************************
 *  http://www.cs.princeton.edu/introcs/78crypto/RSA.java.html
 *  Compilation:  javac RSA.java
 *  Execution:    java RSA N
 *  
 *  Generate an N-bit public and private RSA key and use to encrypt
 *  and decrypt a random message.
 * 
 *  % java RSA 50
 *  public  = 65537
 *  private = 553699199426609
 *  modulus = 825641896390631
 *  message   = 48194775244950
 *  encrpyted = 321340212160104
 *  decrypted = 48194775244950
 *
 *  Known bugs (not addressed for simplicity)
 *  -----------------------------------------
 *  - It could be the case that the message >= modulus. To avoid, use
 *    a do-while loop to generate key until modulus happen to be exactly N bits.
 *
 *  - It's possible that gcd(phi, publicKey) != 1 in which case
 *    the key generation fails. This will only happen if phi is a
 *    multiple of 65537. To avoid, use a do-while loop to generate
 *    keys until the gcd is 1.
 *
 *************************************************************************/

import java.math.BigInteger;
import java.security.SecureRandom;
    

public class CS78cryptoRSA {
   private final static BigInteger one      = new BigInteger("1");
   private final static SecureRandom random = new SecureRandom();

   private BigInteger privateKey;
   private BigInteger publicKey;
   private BigInteger modulus;

   // generate an N-bit (roughly) public and private key
   CS78cryptoRSA(int N) {
      BigInteger p = BigInteger.probablePrime(N/2, random);
      BigInteger q = BigInteger.probablePrime(N/2, random);
      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

      modulus    = p.multiply(q);                                  
      publicKey  = new BigInteger("65537");     // common value in practice = 2^16 + 1
      privateKey = publicKey.modInverse(phi);
   }
   
   CS78cryptoRSA(String privateKey,String publicKey,String modulus) {
	   this.publicKey  = new BigInteger(privateKey,16);
	   this.privateKey = new BigInteger(publicKey,16);
	   this.modulus    = new BigInteger(modulus,16);
   }


   BigInteger encrypt(BigInteger message) {
      return message.modPow(publicKey, modulus);
   }

   BigInteger decrypt(BigInteger encrypted) {
      return encrypted.modPow(privateKey, modulus);
   }

   public String toString() {
	   return new StringBuffer(2048)
	   	.append("public    = " ).append(publicKey.toString(16))  .append( "\n")
	   	.append("private   = " ).append(privateKey.toString(16)) .append( "\n")
	   	.append("modulus   = " ).append(modulus.toString(16))  
	   	.toString();
   }
 
   public static void testRsa(int N){
	   CS78cryptoRSA key = new CS78cryptoRSA(N);
	   System.out.println(key);

	   // create random message, encrypt and decrypt
	   //BigInteger message = new BigInteger(N-1, random);
	   BigInteger message = new BigInteger("java.math.BigInteger".getBytes());

	   //// create message by converting string to integer
	   // String s = "test";
	   // byte[] bytes = s.getBytes();
	   // BigInteger message = new BigInteger(s);
	   //Long.parseLong("java.math.BigInteger");
	   BigInteger encrypt = key.encrypt(message);
	   BigInteger decrypt = key.decrypt(encrypt);
	   System.out.println("message   = " + message);
	   System.out.println("encrpyted = " + encrypt);
	   System.out.println("decrypted = " + decrypt);
	   System.out.println("message   = " + new String(decrypt.toByteArray()));
   }
   
   public static void encode(String msg){
	   String publicKey="10001";
	   String privateKey="4b5dd9f1c4a7cc16d26b636545c7456c8d742fcd20885ac8503fca083fbebdb120dd4d3ea5b63642e509ed9616733f267efec4e2098610db898b0a9f0ff8a463e5ce06c42bb50f50c8feec78362e9d8b28af304ce305692b841fc94f161fed1b8edff1c1439bfa0501dcc58deaa55a45efd2524b653e8e8a1fa47f1cc5eaef9b130d3d0022edfe7246144e4cea11fb3bf3aa486fdd859";
	   String modulusKey="9cde3252de8a454688eaa806e77152836d0ac0191bf4bdf67275b274007ac7a19c7cf6d63185501e02f32ad297a4452fe5a2add123d8f611d330b5cfbe3d4a189ec8f0172ce1a00f8c38bb11279db372e53afe8431369e6e5d3340cc4df07e207671fa745414a1c825c9b5de310b3fb341454dd486b2cd3b49bcdd774cac2ce51b86d41ae86e185df1abec75b4348e9bc38d69d1997cf";
	   
	   CS78cryptoRSA key = new CS78cryptoRSA(publicKey,privateKey,modulusKey);
	   System.out.println(key);

	   //BigInteger message = new BigInteger(N-1, random);
	   BigInteger message = new BigInteger(msg.getBytes());
	   BigInteger encrypt = key.encrypt(message);
	   BigInteger decrypt = key.decrypt(encrypt);
	   System.out.println("message   = " + message.toString(16));
	   System.out.println("encrpyted = " + encrypt.toString(16));
	   System.out.println("decrypted = " + decrypt.toString(16));
	   System.out.println("message   = " + new String(decrypt.toByteArray()));
   }
   public static void main(String[] args) {
	   //testRsa(1204);
	   encode("java.math.BigInteger");
   }
}
