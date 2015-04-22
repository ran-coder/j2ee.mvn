package j2ee.research.java.collection;

import utils.codec.ByteUtil;

/**
 * @author yuanwei
 * @version ctreateTime:2012-7-3 下午3:32:51
 */
public class HashExample {
	/**
	 * Applies a supplemental hash function to a given hashCode, which defends against poor quality hash functions. This is critical because HashMap uses power-of-two length hash tables, that
	 * otherwise encounter collisions for hashCodes that do not differ in lower bits. Note: Null keys always map to hash 0, thus index 0.
	 */
	static int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h^=(h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	static int oldHash(int h) {
		h+=~(h << 9);
		h^=(h >>> 14);
		h+=(h << 4);
		h^=(h >>> 10);
		return h;
	}
	static void debugHash(int h){
		System.out.println("***************** "+h+" *****************");
		System.out.println(ByteUtil.toBinaryString(h,"h"));
		System.out.println(ByteUtil.toBinaryString( (h >>> 20),"(h >>> 20)" ));
		System.out.println(ByteUtil.toBinaryString( h >>> 12  ,"h >>> 12 )" ));
		System.out.println(ByteUtil.toBinaryString( (h >>> 20) ^ (h >>> 12),"(h >>> 20) ^ (h >>> 12)" ));
		System.out.println(ByteUtil.toBinaryString( h^=(h >>> 20) ^ (h >>> 12),"h^=(h >>> 20) ^ (h >>> 12)" ));
		System.out.println(ByteUtil.toBinaryString( (h >>> 7),"(h >>> 7)" ));
		System.out.println(ByteUtil.toBinaryString( (h >>> 4),"(h >>> 4)" ));
		System.out.println(ByteUtil.toBinaryString( h ^ (h >>> 7),"h ^ (h >>> 7)" ));
		System.out.println(ByteUtil.toBinaryString( hash(h),"hash(h)" ));
	}
	/**
	 * Returns index for hash code h.
	 */
	static int indexFor(int h, int length) {
		return h & (length - 1);
	}


	public static void main(String[] args) {
		//String key="key1";int h=key.hashCode();debugHash(h);
		//key="key2";h=key.hashCode();debugHash(h);
		int index=0;
		System.out.println(ByteUtil.toBinaryString(index=indexFor("key1".hashCode(),3)) +":"+index);
		System.out.println(ByteUtil.toBinaryString(index=indexFor("key1".hashCode(),6)) +":"+index);
		System.out.println(ByteUtil.toBinaryString(index=indexFor("key2".hashCode(),3)) +":"+index);
		System.out.println(ByteUtil.toBinaryString(index=indexFor("key2".hashCode(),6)) +":"+index);
		System.out.println(ByteUtil.toBinaryString(index=indexFor("key3".hashCode(),3)) +":"+index);
		System.out.println(ByteUtil.toBinaryString(index=indexFor("key4".hashCode(),3)) +":"+index);
		System.out.println(ByteUtil.toBinaryString(index=indexFor("key5".hashCode(),3)) +":"+index);
		String[] ss={};
		for(String s:ss){
			System.out.println(s);
		}
	}
	
}
