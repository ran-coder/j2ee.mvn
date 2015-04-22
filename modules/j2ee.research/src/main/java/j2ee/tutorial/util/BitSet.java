package j2ee.tutorial.util;


import utils.codec.ByteUtil;

/**
 * @author yuanwei
 * @version ctreateTime:2012-6-27 上午10:59:24
 */
public class BitSet {
	
	/** i 二进制中1的个数 */
	public static long bit1Count(long i) {
		long count=0;
		while(i > 0){
			count+=i & 0x1;
			i>>>=1;
		}
		return count;
	}
	/** i 二进制中1的个数 */
	public static int bitCount(long i) {
		// HD, Figure 5-14
		i=i - ((i >>> 1) & 0x5555555555555555L);
		i=(i & 0x3333333333333333L) + ((i >>> 2) & 0x3333333333333333L);
		i=(i + (i >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
		i=i + (i >>> 8);
		i=i + (i >>> 16);
		i=i + (i >>> 32);
		return (int)i & 0x7f;
	}
	
	// Requires positive x
	public static int stringSize(long x) {
		long p=10;
		for(int i=1;i < 19;i++){
			if(x < p) return i;
			p=10 * p;
		}
		return 19;
	}

	public static void main(String[] args) {
		long number=98555;
		System.out.println(ByteUtil.toBinaryString(number));
		System.out.println(stringSize(number));
		
		System.out.println(bit1Count(number));
		System.out.println(Long.bitCount(number));
		System.out.println(ByteUtil.toBinaryString(0x55555555));
		System.out.println(ByteUtil.toBinaryString(0x33333333));
		System.out.println(ByteUtil.toBinaryString(0x0f0f0f0f));
		System.out.println(ByteUtil.toBinaryString(0x00ff00ff));
		System.out.println(ByteUtil.toBinaryString(0x0000ffff));
		System.out.println(ByteUtil.toBinaryString(-1L >>> -1));
		System.out.println(ByteUtil.toBinaryString(-1L >>> 2));
		System.out.println(ByteUtil.toBinaryString(-1L >>> -2));
		System.out.println(ByteUtil.toBinaryString(-1L >>> -65));
		System.out.println(ByteUtil.toBinaryString(-1L >>> number));
		System.out.println(ByteUtil.toBinaryString(-1L >>> -number));
		System.out.println(ByteUtil.toBinaryString(number=~number));
		System.out.println(ByteUtil.toBinaryString(number&=-1L >>> -number));
	}
	
}
