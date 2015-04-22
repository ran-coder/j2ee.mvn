package j2ee.research.java.collection;

import java.util.BitSet;

import utils.codec.ByteUtil;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-7-26 下午2:25:04
 *   
 */
public class BitSetUsage {
	
	public static void main(String[] args) {
		int leng=64;//(int)Math.pow(10,12)
		leng=(int)Math.pow(10,12);
		BitSet set=new BitSet(leng);
		set.set(1,9);
		set.set(16);
		set.set(17);
		set.set(18);
		System.out.println( ByteUtil.toBinaryString(set.toByteArray()) );
		//System.out.println(set);
		for(long l:set.toLongArray()){
			System.out.println( ByteUtil.toBinaryString(l) );
		}
	}
	
}
