package utils.codec;

import org.apache.commons.codec.binary.Base64;
import utils.ExceptionUtil;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author yuanwei
 * @version ctreateTime:2011-8-29 下午5:42:36
 */
public class ByteUtil {
	public final static byte BYTE_NULL=0;
	public final static char[]	DIGITS={
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
		'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
		'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z', '-', '_', '=', '$', '%', '&', '!', '(',
		')', '*', '+', ',', '.', '/', ':', ';', '\'', '"',
		'<', '#', '>', '?', '@', '[', '\\', ']', '^','`',
		'{', '|', '}', '~'
	};
	public final static char[]	UPPER_DIGITS={
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'A', 'B', 'C', 'C', 'E', 'F'
	};
	/**
	 * Chunk separator per RFC 2045 section 2.1.
	 * <p>
	 * N.B. The next major release may break compatibility and make this field
	 * private.
	 * </p>
	 * 
	 * @see <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045 section
	 *      2.1</a>
	 */
	public static final byte[]	CHUNK_SEPARATOR={ '\r', '\n' };

	/**
	 * This array is a lookup table that translates 6-bit positive integer index
	 * values into their "Base64 Alphabet" equivalents as specified in Table 1
	 * of RFC 2045. Thanks to "commons" project in ws.apache.org for this code.
	 * http://svn.apache.org/repos/asf/webservices/commons/trunk/modules/util/
	 */
	public static final byte[]	STANDARD_ENCODE_TABLE={
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
		'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
		'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
		'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', '+', '/' , '='
	};

	/**
	 * This is a copy of the STANDARD_ENCODE_TABLE above, but with + and /
	 * changed to - and _ to make the encoded Base64 results more URL-SAFE. This
	 * table is only used when the Base64's mode is set to URL-SAFE.
	 */
	public static final byte[]	URL_SAFE_ENCODE_TABLE={
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
		'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
		'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
		'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', '@', '$', '.'
	};

	/**
	 * This array is a lookup table that translates Unicode characters drawn
	 * from the "Base64 Alphabet" (as specified in Table 1 of RFC 2045) into
	 * their 6-bit positive integer equivalents. Characters that are not in the
	 * Base64 alphabet but fall within the bounds of the array are translated to
	 * -1. Note: '+' and '-' both decode to 62. '/' and '_' both decode to 63.
	 * This means decoder seamlessly handles both URL_SAFE and STANDARD base64.
	 * (The encoder, on the other hand, needs to know ahead of time what to
	 * emit). Thanks to "commons" project in ws.apache.org for this code.
	 * http://svn.apache.org/repos/asf/webservices/commons/trunk/modules/util/
	 */
	public static final byte[]	DECODE_TABLE={
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, 62, -1, 62, -1, 63, 52, 53,
		54, 55, 56, 57, 58, 59, 60, 61, -1, -1,
		-1, -1, -1, -1, -1,  0,  1,  2,  3,  4,
		 5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
		15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
		25, -1, -1, -1, -1, 63, -1, 26, 27, 28,
		29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
		39, 40, 41, 42, 43, 44, 45, 46, 47, 48,
		49, 50, 51
	};

	/** Mask used to extract 1 bits, 0000 0001 */
	public static final int		MASK_1BITS					=0x1;
	/** Mask used to extract 2 bits, 0000 0011 */
	public static final int		MASK_2BITS					=0x3;
	/** Mask used to extract 3 bits, 0000 0111 */
	public static final int		MASK_3BITS					=0x7;
	/** Mask used to extract 4 bits, 0000 1111 */
	public static final int		MASK_4BITS					=0xf;
	/** Mask used to extract 5 bits, 0001 1111 */
	public static final int		MASK_5BITS					=0x1f;
	/** Mask used to extract 6 bits, 0011 1111 */
	public static final int		MASK_6BITS					=0x3f;
	/** Mask used to extract 7 bits, 0111 1111 */
	public static final int		MASK_7BITS					=0x7f;
	/** Mask used to extract 8 bits, 1111 1111 */
	public static final int		MASK_8BITS					=0xff;

	public static int maskBitsInt(int count){
		return (1<<count)-1;
	}
	public static long maskBitsLong(int count){
		return (1L<<count)-1;
	}
	public static char toHexChar(int nibble) {
		return DIGITS[(nibble & 0xF)];
	}

	/** 格式化打印二进制 */
	public static String toBinaryString(int number, int leng,String description) {
		// StringBuilder builder=new StringBuilder();
		char[] buf={
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',',// 0-9
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',',// 10-19
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',',// 20-29
				'0', '0', '0', '0', ' ', '0', '0', '0', '0' // 30-38
		};

		int charPos=39;
		int shift=1;
		int radix=1 << shift;
		int mask=radix - 1;
		do{
			if(charPos % 5 == 0){
				charPos--;
			}
			--charPos;
			buf[charPos]=DIGITS[number & mask];
			// System.out.println(charPos+":"+buf[charPos]);
			number>>>=shift;
		}while(number != 0);
		
		if(leng == 8){
			return description(Arrays.copyOfRange(buf, 30, 39),description);
		}else if(leng == 16){
			return description(Arrays.copyOfRange(buf, 20, 39),description);
		}else if(leng == 24){
			return description(Arrays.copyOfRange(buf, 10, 39),description);
		}else{
			return description(Arrays.copyOfRange(buf, 0, 39),description);
		}
	}

	/** 格式化打印二进制 */
	public static String toBinaryString(int number) {
		return toBinaryString(number,32);
	}
	
	/** 格式化打印二进制 */
	public static String toBinaryString(int number,String description) {
		return toBinaryString(number,32,description);
	}
	/** 打印二进制 */
	public static String toBinaryString(byte... bytes) {
		if(bytes == null || bytes.length < 1) return null;
		StringBuilder builder=new StringBuilder(bytes.length * 9);
		for(byte aByte:bytes){
			aByte&=0xff;
			builder
			.append(DIGITS[aByte >> 7 & 0x1])
			.append(DIGITS[aByte >> 6 & 0x1])
			.append(DIGITS[aByte >> 5 & 0x1])
			.append(DIGITS[aByte >> 4 & 0x1]).append(' ')
			.append(DIGITS[aByte >> 3 & 0x1])
			.append(DIGITS[aByte >> 2 & 0x1])
			.append(DIGITS[aByte >> 1 & 0x1])
			.append(DIGITS[aByte      & 0x1]).append(',');
		}
		if(builder.length()>0)builder.setLength(builder.length()-1);
		return builder.toString();
	}
	public static String toBinaryString(byte[] bytes,int start,int end) {
		return (toBinaryString(subBytes(bytes,start,end)));
	}
	/** 打印二进制 */
	public static String toBinaryString(long number, int leng) {
		char[] buf={
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',',// 0-9
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',',// 10-19
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',',// 20-29
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', '\t',// 30-39
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',', // 40-49
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',', // 50-59
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',', // 60-69
				'0', '0', '0', '0', ' ', '0', '0', '0', '0'       // 70-78
		};

		int charPos=buf.length;
		do{
			if(charPos % 5 == 0){
				charPos--;
			}
			--charPos;
			buf[charPos]=DIGITS[(byte)(number&0x1)];
			// System.out.println(charPos+":"+buf[charPos]);
			number>>>=1;
		}while(number != 0);
		//for(int i=1;i<9;i++){}
		if(leng == 8){
			return new String(buf,70,9);
		}else if(leng == 16){
			return new String(buf,60,19);
		}else if(leng == 24){
			return new String(buf,50,29);
		}else if(leng == 32){
			return new String(buf,40,39);
		}else{
			return new String(buf);
		}
	}
	public static String toBinaryString(char... chars){
		return toBinaryString(chars2Bytes(chars));
	}
	/** 打印二进制 */
	public static String toBinaryString(long number) {
		return toBinaryString(number,64);
	}
	/** 打印二进制 */
	//TODOO ByteUtil.toChars(BitSet.toLongArray()) 报错
	public static char[] toChars(long... numbers) {
		if(numbers!=null&&numbers.length>0){
			CharBuffer buffer=CharBuffer.allocate(numbers.length*78);
			for(long l:numbers){
				buffer.put(toChars(l));
			}
			try{
				return buffer.array();
			}finally{
				buffer.clear();
			}
			
		}
		return null;
	}

	/** 打印二进制 */
	public static char[] toChars(long number) {
		char[] buf={
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',',// 0-9
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',',// 10-19
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',',// 20-29
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', '\t',// 30-39
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',', // 40-49
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',', // 50-59
				'0', '0', '0', '0', ' ', '0', '0', '0', '0', ',', // 60-69
				'0', '0', '0', '0', ' ', '0', '0', '0', '0'       // 70-78
		};

		int charPos=buf.length;
		do{
			if(charPos % 5 == 0){
				charPos--;
			}
			--charPos;
			buf[charPos]=DIGITS[(byte)(number&0x1)];
			// System.out.println(charPos+":"+buf[charPos]);
			number>>>=1;
		}while(number != 0);
		return buf;
	}
	public static String toString(byte... bytes){
		if(bytes == null || bytes.length < 1) return null;
		StringBuilder builder=new StringBuilder(bytes.length*5);
		for(byte abyte:bytes){
			builder
			.append(abyte&0xff)
			.append(',')
			;
		}
		if(builder.length()>0)builder.setLength(builder.length()-1);
		return builder.toString();
	}

	/** 输出byte[]: {0x1,0x2 ,0x3 ,0x4,0xff} */
	public static String toHexString(byte... bytes){
		if(bytes == null || bytes.length < 1) return null;
		StringBuilder builder=new StringBuilder(bytes.length*5);
		for(byte abyte:bytes){
			abyte&=0xff;
			builder.append('0').append('x')
			.append(DIGITS[abyte>>4 & MASK_4BITS])
			.append(DIGITS[abyte    & MASK_4BITS])
			.append(',')
			;
		}
		if(builder.length()>0)builder.setLength(builder.length()-1);
		return builder.toString();
	}
	/** 输出byte[]: {01,02 ,03 ,04,FF} */
	public static String toHexOmitString(byte[] bytes,int ingoreLength){
		if(bytes == null || bytes.length < 1) return null;
		StringBuilder builder=new StringBuilder(bytes.length*5);
		int count=0;
		for(byte abyte:bytes){
			if(count>ingoreLength){
				if(builder.length()>0)builder.setLength(builder.length()-1);
				builder.append('.').append('.').append('.');
				break;
			}
			abyte&=0xff;
			builder//.append('0').append('x')
			.append(UPPER_DIGITS[abyte>>4 & MASK_4BITS])
			.append(UPPER_DIGITS[abyte    & MASK_4BITS])
			.append(',')
			;
			count++;
		}
		return builder.toString();
	}
	/** 输出byte[]: {0x1,0x2 ,0x3 ,0x4,0xff} */
	public static String toHexStringWithoutPrefix(byte... bytes){
		if(bytes == null || bytes.length < 1) return null;
		StringBuilder builder=new StringBuilder(bytes.length*5);
		for(byte abyte:bytes){
			abyte&=0xff;
			builder//.append('0').append('x')
			.append(DIGITS[abyte>>4 & MASK_4BITS])
			.append(DIGITS[abyte    & MASK_4BITS])
			.append(',')
			;
		}
		if(builder.length()>0)builder.setLength(builder.length()-1);
		return builder.toString();
	}
	public static String int2HexString(int number){
		byte[] bytes = Number.int2Bytes(number);

		StringBuilder builder=new StringBuilder(bytes.length*2+2).append('0').append('x');
		for(byte aByte:bytes){
			if(aByte!=0)
				builder
				.append(DIGITS[aByte>>4 & MASK_4BITS])
				.append(DIGITS[aByte    & MASK_4BITS])
				;
		}
		return builder.toString();
	}
	public static String long2HexString(long number){
		byte[] bytes = Number.long2Bytes(number);
		StringBuilder builder=new StringBuilder(bytes.length*2+2).append('0').append('x');
		for(byte aByte:bytes){
			if(aByte!=0)
				builder
				.append(DIGITS[aByte>>4 & MASK_4BITS])
				.append(DIGITS[aByte    & MASK_4BITS])
				;
		}
		return builder.toString();
	}

	/** 修复byte[]长度,bytes.length>=length返回 */
	public static byte[] fixBytesLength(byte[] bytes,int newLength){
		if(bytes==null||bytes.length>=newLength)return bytes;
		//Arrays.fill(bytes,0,newLength-bytes.length,(byte)0x0);
		byte[] newBytes=new byte[newLength];
		for(int i=0,j=bytes.length,offset=newLength-bytes.length;i<j;i++){
			newBytes[i+offset]=bytes[i];
		}
		//printBinaryString(newBytes);
		//printBinaryString(bytes);
		return newBytes;
	}
	/** 改变byte[]长度{0x0,0x1} -->{0x0,0x1,0x0} */
	public static byte[] newBytes(byte[] bytes,int newLength){
		byte[] copy = new byte[newLength];
		System.arraycopy(bytes, 0, copy, 0,Math.min(bytes.length, newLength));
		return copy;
	}
	/** 去掉高位的0000: 0x00,0x00,0x00,0x00,0x64,0x56,0x66,0x56,0x00 --> 0x64,0x56,0x66,0x56*/
	public static byte[] trim(byte... bytes){
		if(bytes==null)return null;
		int beginIndex=-1;
		int endIndex=-1;
		for(int i=0,j=bytes.length;i<j;i++){
			if(bytes[i]!=BYTE_NULL){
				beginIndex=i;
				break;
			}
		}
		for(int i=bytes.length-1;i>beginIndex;i--){
			if(bytes[i]!=BYTE_NULL){
				endIndex=i;
				break;
			}
		}
		//TODO test it.
		return subBytes(bytes,beginIndex,endIndex);
	}
	/**
	 * @param bytes
	 * @param beginIndex  0开始
	 * @param endIndex -1 到末尾
	 * @return
	 */
	public static byte[] subBytes(byte[] bytes,int beginIndex, int endIndex){
		if(bytes==null||bytes.length<1)return bytes;
		if(endIndex==-1)endIndex=bytes.length-1;
		int newLength = endIndex - beginIndex+1;
		if (newLength < 0)
			throw ExceptionUtil.newIllegalStateException("endIndex must > beginIndex :"+beginIndex + " > " + endIndex);;
		byte[] copy = new byte[newLength];
		System.arraycopy(bytes, beginIndex, copy, 0,Math.min(bytes.length - beginIndex, newLength));
		return copy;
		/*
		byte[] newBytes=new byte[endIndex-beginIndex+1];
		for(int i=0,j=newBytes.length;i<j;i++){
			newBytes[i]=bytes[beginIndex+i];
		}
		//printHexString(bytes);
		//printHexString(newBytes);
		return newBytes;
		*/
	}
	/**
	 * @param bytes
	 * @param beginIndex  0开始 到末尾
	 * @return
	 */
	public static byte[] subBytes(byte[] bytes,int beginIndex){
		return subBytes(bytes,beginIndex,-1);
	}

	public static byte[] chars2Bytes(char... chars){
		if(chars==null||chars.length<1)return null;
		int len=chars.length;
		byte[] bytes=new byte[chars.length*2];
		for(int i=0;i<len;i++){
			bytes[2*i+0]=(byte)((chars[i]>>8)&0xff);
			bytes[2*i+1]=(byte)(chars[i]&0xff);
		}
		return bytes;
	}
	public static char[] bytes2Chars(byte... bytes){
		if(bytes==null||bytes.length<1)return null;
		int len=bytes.length;
		char[] chars=new char[len/2+ (len%2==0?0:1)];
		for(int i=0,j=0;i<len;i+=2){
			if(i==(len-1)){
				chars[j++]=(char)(bytes[i]);
			}else{
				chars[j++]=(char)( (bytes[i]<<8) | (bytes[i+1]&0xff) );
			}
		}
		//System.out.println(chars.length);
		/*if(len%2==0){
			for(int i=0,j=0;i<len;i+=2){
				chars[j++]=(char)( (bytes[i]<<8) | bytes[i+1] );
			}
		}else{
			for(int i=0,j=0;i<len-1;i+=2){
				chars[j++]=(char)( (bytes[i]<<8) | bytes[i+1] );
			}
			chars[chars.length-1]=(char)(bytes[len-1]);
		}*/
		return chars;
	}
	public static int char2Int(char ch){
		return (int)ch;
	}
	public static byte[] getBytes(ByteBuffer buff){
		if(buff.limit()<1)return null;
		/*byte[] overByte=new byte[buff.limit()];
		for(int index=0,j=overByte.length;index < j;index++){
			overByte[index]=buff.get(index);
		}*/
		return Arrays.copyOfRange(buff.array(),0,buff.limit());
	}
	static String description(char[] chars,String description){
		if(description==null||description.trim().length()<1)return new String(chars);
		return new StringBuilder(chars.length+1+description.length())
			.append(chars).append(':').append(description.toCharArray()).toString();
	}
 	public static class Number{
		/** ignoreOutOfRange是否忽略超界 */
		public static byte[] ints2Bytes(int[] ints,boolean ignoreOutOfRange){
			if(ints==null||ints.length<1)return null;
			int length=ints.length;
			if(ignoreOutOfRange){
				byte[] bytes=new byte[length];
				for(int i=0;i<length;i++){
					bytes[i]=(byte)ints[i];
				}
				return bytes;
			}else{
				byte[] bytes=new byte[length*4];
				for(int i=length-1,j=bytes.length;i>=0;i--){
					if(ints[i]==0){
						bytes[--j]=0;
					}else{
						while(true){
							if(ints[i]==0)break;
							bytes[--j]=(byte)(ints[i]&0xff);
							ints[i]>>>=8;
						}
					}
				}
				return trim(bytes);
			}
		}
		/** 忽略超界 */
		public static byte[] ints2Bytes(int... ints){
			return ints2Bytes(ints,true);
		}
		public static int[] bytes2Ints(byte... bytes){
			if(bytes==null||bytes.length<1)return null;
			int length=bytes.length;
			int[] ints=new int[length];
			for(int i=0;i<length;i++){
				ints[i]=bytes[i]&0xff;
			}
			return ints;
		}
		public static List<Integer> bytes2IntList(byte... bytes){
			if(bytes==null||bytes.length<1)return null;
			List<Integer> list=new ArrayList<Integer>();
			for(byte aByte:bytes){
				list.add((int)aByte);
			}
			return list;
		}
		public static byte[] int2Base64(int number){
			return Base64.encodeBase64URLSafe(int2Bytes(number));
		}
		public static byte[] long2Base64(long number){
			return Base64.encodeBase64URLSafe(long2Bytes(number));
		}
		public static String long2Base64String(long number){
			return Base64.encodeBase64URLSafeString(trim(long2Bytes(number)));
		}
		/** long类型转成byte数组 */
		public static byte[] long2Bytes(long number) {
			byte[] bytes = new byte[8];
			for (int i = 7; i >=0; i--) {
				bytes[i] = (byte) number;
				number >>= 8;
			}
			return bytes;
		}
		/** 打印二进制 */
		public static byte[] toBytes(long... numbers) {
			if(numbers!=null&&numbers.length>0){
				ByteBuffer buffer=ByteBuffer.allocate(numbers.length*78*2);
				//CharBuffer buffer=CharBuffer.allocate(numbers.length*78);
				for(long l:numbers){
					buffer.put(ByteUtil.Number.long2Bytes(l));
				}
				try{
					return buffer.array();
				}finally{
					buffer.clear();
				}
			}
			return null;
		}
		/** byte数组转成long */
		public static long bytes2Long(byte... bytes) {
			if(bytes.length<1)return 0L;
			//bytes=fixBytesLength(bytes,8);
			long result=0L;
			for(int i=0,j=bytes.length;i<j;i++){
				result|=((long)bytes[j-i-1]&0xff) <<(8*i);
			}
			return result;
			/*return ((long)(bytes[0] &0xff) <<8*7)|
					((long)(bytes[1] &0xff) <<8*6)|
					((long)(bytes[2] &0xff) <<8*5)|
					((long)(bytes[3] &0xff) <<8*4)|
					((long)(bytes[4] &0xff) <<8*3)|
					((long)(bytes[5] &0xff) <<8*2)|
					((long)(bytes[6] &0xff) <<8*1) |
					((long)(bytes[7] &0xff) <<8*0)
					;*/
		}
		/** 注释：int到字节数组的转换*/
		public static byte[] int2Bytes(int number) {
			byte[] bytes = new byte[4];
			for (int i = 3; i >=0; i--) {
				bytes[i] = (byte) number;
				number >>= 8;
			}
			return bytes;
		}
		/** 字节数组到int的转换 */
		public static int bytes2Int(byte... bytes) {
			bytes=fixBytesLength(bytes,4);
			return
					((bytes[0] &0xff) <<24)|
					((bytes[1] &0xff) <<16)|
					((bytes[2] &0xff) <<8 )|
					((bytes[3] &0xff));
		}

		/** short到字节数组的转换 */
		public static byte[] short2Bytes(short number) {
			byte[] bytes = new byte[2];
			bytes[0] = (byte) ((number >> 8)  & 0xFF);
			bytes[1] = (byte) ( number & 0xFF);
			return bytes;
		}
		/** 字节数组到short的转换 */
		public static short bytes2Short(byte... bytes) {
			bytes=fixBytesLength(bytes,2);
			return  (short) (  ((bytes[0] &0xff) <<8 ) | ((bytes[1] &0xff))   );
		}

		/**
		 * 将 Little-Endian 的字节数组转为 int 类型的数据<br />  Little-Endian 低位字节放在低地址处
		 * @param bytes      字节数组
		 * @param start    需要转换的开始索引位数
		 * @param len      需要转换的字节数量
		 * @return 指定开始位置和长度以 LE 方式表示的 int 数值
		 */
		public static int bytes2IntLE(byte[] bytes, int start, int len) {
			return bytes2Int(bytes,start,len,false);
		}
		public static int bytes2IntLE(byte... bytes) {
			return bytes2Int(bytes,0,bytes.length,false);
		}
		/**
		 * 将 Big-Endian 的字节数组转为 int 类型的数据<br /> Big-Endian 高位字节放在低地址处
		 * @param bytes            字节数组
		 * @param start          需要转换的开始索引位数
		 * @param len            需要转换的字节数量
		 * @return 指定开始位置和长度以 BE 方式表示的 int 数值
		 */
		public static int bytes2IntBE(byte[] bytes, int start, int len) {
			return bytes2Int(bytes,start,len,true);
		}
		public static int bytes2IntBE(byte... bytes) {
			return bytes2Int(bytes,0,bytes.length,true);
		}
		/**
		 * 将字节数组转为 Java 中的 int 数值
		 * @param bytes            字节数组
		 * @param start          需要转换的起始索引点
		 * @param len            需要转换的字节长度
		 * @param isBigEndian    是否是 BE（true -- BE 序，false -- LE 序）
		 * @return
		 */
		private static int bytes2Int(byte[] bytes, int start, int len, boolean isBigEndian) {
			int n=0;
			for(int i=start,j=start + len % (Integer.SIZE / Byte.SIZE + 1);i < j;i++){
				n|=(bytes[i] & 0xff) << ((isBigEndian ? (j - i - 1) : i) * Byte.SIZE);
			}
			return n;
		}
	}
	public static class BaseNUtil{
		/** 8位截取6位 */
		public static byte[] base64Encode(byte... bytes){
			if(bytes==null||bytes.length<1)return null;
			int len=bytes.length;
			int count=len/3+(len%3==0?0:1);//bytes分组的个数
			byte[] result=new byte[count*4];
			//System.out.println(len+":len%3="+(len%3));
			for(int i=0;i<count;i++){
				if(i==(count-1)&&len%3!=0){
					if(len%3==1){
						result[i*4+0]=URL_SAFE_ENCODE_TABLE[ (  bytes[i*3+0] >> 2) & MASK_6BITS ];
						result[i*4+1]=URL_SAFE_ENCODE_TABLE[ ( (bytes[i*3+0]&MASK_2BITS) << 4)  ];
						result[i*4+2]=URL_SAFE_ENCODE_TABLE[64];//没分到组,默认填充值
						result[i*4+3]=URL_SAFE_ENCODE_TABLE[64];
					}else if(len%3==2){
						result[i*4+0]=URL_SAFE_ENCODE_TABLE[ (  bytes[i*3+0] >> 2) & MASK_6BITS ];
						result[i*4+1]=URL_SAFE_ENCODE_TABLE[ ( (bytes[i*3+0]&MASK_2BITS) << 4) + ( (bytes[i*3+1] >> 4) & MASK_4BITS)   ];
						result[i*4+2]=URL_SAFE_ENCODE_TABLE[ ( (bytes[i*3+1]&MASK_4BITS) << 2)   ];
						result[i*4+3]=URL_SAFE_ENCODE_TABLE[64];//没分到组,默认填充值
					}
				}else{
					result[i*4+0]=URL_SAFE_ENCODE_TABLE[ (  bytes[i*3+0] >> 2) & MASK_6BITS ];
					result[i*4+1]=URL_SAFE_ENCODE_TABLE[ ( (bytes[i*3+0]&MASK_2BITS) << 4) + ( (bytes[i*3+1] >> 4) & MASK_4BITS)   ];
					result[i*4+2]=URL_SAFE_ENCODE_TABLE[ ( (bytes[i*3+1]&MASK_4BITS) << 2) + ( (bytes[i*3+2] >> 6) & MASK_2BITS)   ];
					result[i*4+3]=URL_SAFE_ENCODE_TABLE[   (bytes[i*3+2]&MASK_6BITS)     ];
				}
				//printBinaryString(bytes,i*3,i*3+2);
				//printBinaryString(result,i*4,i*4+3);
			}
			return result;
		}
		public static String base64EncodeString(byte... bytes){
			if(bytes==null)return null;
			return new String(base64Encode(bytes));
		}
		/** 去掉高位的0000: 0x00,0x00,0x00,0x00,0x64,0x56,0x66,0x56 --> 0x64,0x56,0x66,0x56*/
		public static String base64EncodeStringTrim(byte... bytes){
			return base64EncodeString(trim(bytes));
		}
	}
	public static class BitUtil{
		
	}
	static class Beta {

	}

	public static void main(String[] args) throws Exception {
		System.out.println(toBinaryString(16,"16"));
		System.out.println(description("123".toCharArray(),"123"));
		System.out.println(CharUtil.toHexString(bytes2Chars( new byte[]{0x4e,0x2d} )));
		System.out.println(CharUtil.toHexString(bytes2Chars( new byte[]{0x4e,0x2d,0x1} )));
		System.out.println(CharUtil.toHexString(bytes2Chars( new byte[]{0x1} )));
		System.out.println(CharUtil.toHexString(bytes2Chars( chars2Bytes(new char[]{'a','b','c','中'}) )));
		System.out.println(Number.bytes2Long(Base64.decodeBase64("HdGPNHA")));
		System.out.println(Number.bytes2Long(Base64.decodeBase64("0d3nt8t")));
		System.out.println(Number.bytes2Long(Base64.decodeBase64("gD89lHi")));
		System.out.println(toBinaryString('a',8));
		System.out.println(toBinaryString('A',8));
		System.out.println(toBinaryString('h',8));
		System.out.println(toBinaryString('H',8));
		System.out.println(toBinaryString('z',8));
		System.out.println(toBinaryString('Z',8));
		String str="中文";
		System.out.println(toBinaryString(str.getBytes()));
		System.out.println(toBinaryString(str.getBytes("UTF-8")));
		str="涓枃";
		System.out.println(toBinaryString(str.getBytes()));
		System.out.println(toBinaryString(str.getBytes("UTF-8")));
		
		System.out.println(toChars(10L).length);
		
		System.out.println(toBinaryString( ByteUtil.Number.toBytes( new long[]{0xf0,0x0f} )  ) );
	}
}
