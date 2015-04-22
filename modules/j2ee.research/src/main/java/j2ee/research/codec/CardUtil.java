package j2ee.research.codec;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import utils.validate.Validators;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-19 17:02
 * To change this template use File | Settings | File Templates.
 */
public class CardUtil{
	private static String sKey="010203ab05060708";
	private static String ipv="010203ab05060708";
	private static final Base32 base32=new Base32(0, null,false);
	//private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	private static final char[] DIGITS_UPPER = {'Q','W','E','R','T','Y','U','I','V','P','A','S','D','F','G','H'};


	public final static StringBuilder cardSid(String sid,String key){
		StringBuilder builder=new StringBuilder(1000);
		String src=sid+"_HeLlO_b-O.oky_"+key;
		//fix(builder, encodeHex(DigestUtils.md5(src)),4).append(',');
		fix(builder, encodeHex(DigestUtils.sha(src)),4);
		//builder.append(encodeHex(DigestUtils.md5(src))).append(',')
		//.append(encodeHex(DigestUtils.sha(src)))
		;

		return builder;
	}
	protected static char[] encodeHex(byte[] data) {
		/*char[] toDigits=DIGITS_UPPER;
		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}*/
		return Arrays.copyOf(base32.encodeToString(data).toCharArray(),20);
	}
	// 解密
	public static String decrypt(String sSrc){
		try{
			// 判断Key是否正确
			if(sKey==null){
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if(sKey.length()!=16){
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw=sKey.getBytes("ASCII");
			SecretKeySpec skeySpec=new SecretKeySpec(raw, "AES");
			Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv=new IvParameterSpec(ipv
					.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1=Base64.decodeBase64(sSrc);//先用base64解密eAWibmwQLWCxihboMCBkhGSqFw34r6qzTGiOVkLGZLU=
			try{
				byte[] original=cipher.doFinal(encrypted1);
				String originalString=new String(original);
				return originalString;
			}catch(Exception e){
				//System.out.println(e.toString());
				e.printStackTrace();
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			// System.out.println(ex.toString());
			return null;
		}
	}

	public final static String md5(String input){
		try{
			byte[] btInput=input.getBytes();
			MessageDigest mdInst=MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md=mdInst.digest();
			StringBuilder sb=new StringBuilder();
			for(int i=0; i<md.length; i++){
				int val=((int) md[i]) & 0xff;
				if(val<16) sb.append("0");
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		}catch(Exception e){
			return null;
		}
	}


	/**
	 * 8位卡号生成12位
	 * @param cardCode 卡号8位
	 * @param key ProductCardBatch中的key
	 * @return
	 */
	public static String genarateCardNo12(String cardCode, String key){
		String privateKey=key==null ? "" : decrypt(key);
		String md5Str=md5(cardCode+privateKey);
		//System.out.println(md5Str.equals(DigestUtils.md5Hex(cardCode+privateKey)));
		int lastNum=NumUtil.genarateCode(cardCode);

		//String  asciis=String.valueOf((Integer.valueOf(((char)md5Str.charAt(0)))))+String.valueOf((Integer.valueOf(((char)md5Str.charAt(1)))));
		String asciis=(Integer.valueOf(md5Str.charAt(0)).toString()+Integer.valueOf(md5Str.charAt(1)).toString()).substring(0, 2);
		//String numStr =""+lastNum+asciis12+NumUtil.lastNum(asciis12+lastNum);
		return new StringBuilder(12).append(cardCode).append(lastNum).append(asciis)
		                            .append(NumUtil.lastNum(asciis+lastNum))
		                            .toString()
				;
	}

	public static StringBuilder fix(StringBuilder builder, String src, int fixMaxLength){
		//StringBuilder builder=new StringBuilder(src==null?0);
		if(!Validators.isNullOrEmpty(src)){
			char[] chars=src.toCharArray();
			int maxOffset=chars.length;//0-maxOffset
			int offset=0;
			while(offset<maxOffset){
				if( (maxOffset-offset)>fixMaxLength)
					builder.append(Arrays.copyOfRange(chars,offset,offset+fixMaxLength)).append(' ');
				else{
					builder.append(Arrays.copyOfRange(chars,offset,maxOffset));
				}
				offset+=fixMaxLength;
			}
		}
		return builder;
	}
	public static StringBuilder fix(StringBuilder builder, char[] chars, int fixMaxLength){
		//StringBuilder builder=new StringBuilder(src==null?0);
		if(!Validators.isNullOrEmpty(chars)){
			//char[] chars=src.toCharArray();
			int maxOffset=chars.length;//0-maxOffset
			int offset=0;
			while(offset<maxOffset){
				if( (maxOffset-offset)>fixMaxLength)
					builder.append(Arrays.copyOfRange(chars,offset,offset+fixMaxLength)).append(' ');
				else{
					builder.append(Arrays.copyOfRange(chars,offset,maxOffset));
				}
				offset+=fixMaxLength;
			}
		}
		return builder;
	}
	public static String readLine(String line){
		//12000039,ATxfKRJop7cSr0AMKxgknBGYHti540TAN/xl2vdYyq8=
		if(line!=null&& (line=line.trim()).length()>0){
			String sid=line.substring(0,8);
			String key=line.substring(9);
			StringBuilder builder=new StringBuilder(1000);
			builder.append(sid).append(',')
				///.append(genarateCardNo12(sid, key)).append(',')
				///.append(cardSid(sid,key))
			//.append(DigestUtils.md5Hex(sid+key)).append(',')
			//.append(DigestUtils.shaHex(sid+key))
			;
			fix(builder, genarateCardNo12(sid, key), 4).append(',').append(cardSid(sid, key));
			return builder.toString();
		}
		return null;
	}
}
