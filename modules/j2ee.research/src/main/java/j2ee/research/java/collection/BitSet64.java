package j2ee.research.java.collection;

/**
 * @author admin
 * 只存贮64位long
 */
public class BitSet64 {

	// <editor-fold defaultstate="collapsed" desc="Static Fields">
	private final static int	ADDRESS_BITS_PER_WORD	=6;
	private final static int	BITS_PER_WORD				=1 << ADDRESS_BITS_PER_WORD;
	//private final static int	BIT_INDEX_MASK				=BITS_PER_WORD - 1;

	/* Used to shift left or right for a partial word mask */
	private static final long	WORD_MASK					=0xffffffffffffffffL;
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Fields">
	protected long					bitset						=0;

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Constructors">
	protected BitSet64(BitSet64 o) {
		bitset=o.bitset;
	}

	public BitSet64(long bitset) {
		this.bitset=bitset;
	}

	public BitSet64() {
		bitset=0;
	}

	public BitSet64(java.util.BitSet bitset) {
		for(int i=bitset.nextSetBit(0);i >= 0 && i < 64;i=bitset.nextSetBit(i + 1)){
			set(i);
		}
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Methods">
	public boolean get(long index) {
		return (bitset & (1L << index)) != 0;
	}

	public void set(long index) {
		bitset|=(1L << index);
	}

	public void set(long fromIndex, long toIndex) {
		long firstWordMask=WORD_MASK << fromIndex;
		long lastWordMask=WORD_MASK >>> -toIndex;
		bitset|=(firstWordMask & lastWordMask);
	}

	public void set(long index, boolean value) {
		if(value){
			set(index);
		}else{
			clear(index);
		}
	}

	public void set(long fromIndex, long toIndex, boolean value) {
		if(value){
			set(fromIndex,toIndex);
		}else{
			clear(fromIndex,toIndex);
		}
	}

	public void clear() {
		bitset=0;
	}

	public void clear(long index) {
		bitset&=~(1L << index);
	}

	public void clear(long fromIndex, long toIndex) {
		long firstWordMask=WORD_MASK << fromIndex;
		long lastWordMask=WORD_MASK >>> -toIndex;
		bitset&=~(firstWordMask & lastWordMask);
	}

	public long length() {
		return (BITS_PER_WORD - Long.numberOfLeadingZeros(bitset));
	}

	public long cardinality() {
		return Long.bitCount(bitset);
	}

	public void and(BitSet64 o) {
		bitset&=o.bitset;
	}

	public void or(BitSet64 o) {
		bitset|=o.bitset;
	}

	public void xor(BitSet64 o) {
		bitset^=o.bitset;
	}

	public void andNot(BitSet64 o) {
		bitset&=~o.bitset;
	}

	public void flip(long fromIndex, long toIndex) {
		long firstWordMask=WORD_MASK << fromIndex;
		long lastWordMask=WORD_MASK >>> -toIndex;
		bitset^=(firstWordMask & lastWordMask);
	}

	public long nextSetBit(long fromIndex) {
		long word=bitset & (WORD_MASK << fromIndex);

		if(word != 0){ return Long.numberOfTrailingZeros(word); }

		return -1;
	}

	public long nextClearBit(long fromIndex) {
		long word=~bitset & (WORD_MASK << fromIndex);

		if(word != 0){ return Long.numberOfTrailingZeros(word); }

		return -1;
	}

	public long value() {
		return bitset;
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Object Overrides">
	@Override
	public BitSet64 clone() {
		return new BitSet64(bitset);
	}

	public boolean equals(BitSet64 o) {
		return bitset == o.bitset;
	}

	@Override
	public boolean equals(Object o) {
		/**
		 * not safe; doing it anyway
		 */
		return bitset == ((BitSet64)o).bitset;
	}

	@Override
	public int hashCode() {
		// return (int) bitset;
		long h=1234;
		h^=bitset;

		return (int)((h >> 32) ^ h);
	}

	@Override
	public String toString() {
		StringBuilder b=new StringBuilder();
		b.append('{');

		long i=nextSetBit(0);
		if(i != -1){
			b.append(i);
			for(i=nextSetBit(i + 1);i >= 0;i=nextSetBit(i + 1)){
				long endOfRun=nextClearBit(i);
				do{
					b.append(", ").append(i);
				}while(++i < endOfRun);
			}
		}

		b.append('}');
		return b.toString();
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Static Get and Set Methods">

	public static long or(long bitset1, long bitset2) {
		return bitset1 | bitset2;
	}

	public static long setBits(long bitset, int fromIndex, int toIndex) {
		long firstWordMask=WORD_MASK << fromIndex;
		long lastWordMask=WORD_MASK >>> -toIndex;
		bitset|=(firstWordMask & lastWordMask);
		return bitset;
	}

	public static boolean getBit(long bitset, int index) {
		return (bitset & (1L << index)) != 0;
	}

	public static long setBit(long bitset, int index) {
		long result=bitset | (1L << index);
		return result;
	}

	public static long clearBits(long bitset, int fromIndex, int toIndex) {
		long firstWordMask=WORD_MASK << fromIndex;
		long lastWordMask=WORD_MASK >>> -toIndex;
		bitset&=~(firstWordMask & lastWordMask);
		return bitset;
	}

	public static long clearBit(long bitset, int index) {
		long result=bitset & ~(1L << index);
		return result;
	}

	public static int cardinality(long bitset) {
		return Long.bitCount(bitset);
	}

	public static int hashCode(long bitset) {
		// return (int) bitset;
		long h=1234;
		h^=bitset;

		return (int)((h >> 32) ^ h);
	}

	/**
	 * Given a bitset represented as a long, find the index of the last bit which
	 * is set. -1 is returned if no bits are set.
	 * 
	 * @param bitset
	 *           the bitset to search.
	 * @return the index of the last set bit.
	 */
	public static int lastSetBit(long bitset) {
		return previousSetBit(bitset,64);
	}

	/**
	 * Given a bitset and a position, find the last set bit which precedes that
	 * index. -1 is returned if no set bits precede the index.
	 * 
	 * @param bitset
	 * @param index
	 * @return the index of the last set bit before <code>index<code>.
	 */
	public static int previousSetBit(long bitset, int index) {
		int lastSetBit=-1;
		for(int i=nextSetBit(bitset,0);i > -1 && i < index;i=nextSetBit(bitset,i + 1)){
			lastSetBit=i;
		}
		return lastSetBit;
	}

	public static int firstSetBit(long bitset) {
		return nextSetBit(bitset,0);
	}

	public static int nextSetBit(long bitset, int fromIndex) {
		long word=bitset & (WORD_MASK << fromIndex);

		if(word != 0){ return Long.numberOfTrailingZeros(word); }

		return -1;
	}

	public static int firstClearBit(long bitset) {
		return nextClearBit(bitset,0);
	}

	public static int nextClearBit(long bitset, int fromIndex) {
		long word=~bitset & (WORD_MASK << fromIndex);

		if(word != 0){ return Long.numberOfTrailingZeros(word); }

		return -1;
	}

	public static String toString(long bitset) {
		StringBuilder b=new StringBuilder();
		b.append('{');

		int i=nextSetBit(bitset,0);
		if(i != -1){
			b.append(i);
			for(i=nextSetBit(bitset,i + 1);i >= 0;i=nextSetBit(bitset,i + 1)){
				int endOfRun=nextClearBit(bitset,i);
				do{
					b.append(", ").append(i);
				}while(++i < endOfRun);
			}
		}

		b.append('}');
		return b.toString();
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Static Convenience Methods">
	/**
	 * Convert <code>bitset</code> into a long based on the set bits among its
	 * first 64 bits. The remaining bits of <code>bitset</code> are ignored.
	 * 
	 * @param bitset
	 *           the bitset
	 * @return a long representation of the first 64 bits of <code>bitset</code>
	 */
	public static long toLong(java.util.BitSet bitset) {
		long ret=0;

		for(int i=bitset.nextSetBit(0);i > -1 && i < 64;i=bitset.nextSetBit(i + 1)){

			ret=BitSet64.setBit(ret,i);
		}

		return ret;
	}

	/**
	 * Convert <code>bitset</code> into a bitset by setting the same bits in a
	 * bitset.
	 * 
	 * @param bitset
	 *           the bitset
	 * @return a BitSet representation of <code>bitset</code>.
	 */
	public static java.util.BitSet toBitSet(long bitset) {
		java.util.BitSet ret=new java.util.BitSet();
		for(int i=firstSetBit(bitset);i > -1;i=nextSetBit(bitset,i + 1)){
			ret.set(i);
		}

		return ret;
	}
	// </editor-fold>
}