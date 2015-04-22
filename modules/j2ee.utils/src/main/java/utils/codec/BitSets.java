package utils.codec;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.core.BitSet;

public class BitSets {
	protected static Logger log=LoggerFactory.getLogger(BitSets.class);
	public static BitSet enum2BitSet(Enum<?>... enums){
		if(enums!=null&&enums.length>0){
			BitSet set=new BitSet();
			for(Enum<?> e:enums){
				set.set(e.ordinal());
			}
			int cardinality=set.cardinality();
			log.debug(ByteUtil.toBinaryString(set.toByteArray())+":"+set.toString()+"="+cardinality);
			log.debug(Arrays.toString(set.toLongArray())+","+Base64.encodeBase64URLSafeString(set.toByteArray()));
			//System.out.println(ByteUtil.toBinaryString(set.hashCode())+":"+set.hashCode());
			return set;
		}
		return null;
	}
	public static byte[] enumToByteArray(BitSet set){
		return set==null?null:set.toByteArray();
	}
	public static String toStringBase64URLSafe(BitSet set){
		return set==null?null:Base64.encodeBase64URLSafeString(set.toByteArray());
	}
	public static String toStringUTF8(BitSet set){
		return set==null?null:new String(set.toByteArray()) ;
	}
	public static boolean contains(String base64URLSafeString,int enumOrdinal){
		boolean result=Boolean.FALSE;
		if(base64URLSafeString!=null&&base64URLSafeString.trim().length()>1){
			
		}
		return result;
	}
	public static boolean contains(byte[] bytes,int enumOrdinal){
		boolean result=Boolean.FALSE;
		if(bytes!=null&&bytes.length>1){
			BitSet set=BitSet.valueOf(bytes);
			if(set!=null)result=set.get(enumOrdinal);
		}
		return result;
	}
	public static void main(String[] args) {
		int leng=64;//(int)Math.pow(10,12)
		leng=(int)Math.pow(10,12);
		BitSet set=new BitSet(leng);
		set.set(1,9);
		set.set(16);
		set.set(17);
		set.set(18);
		set.set(108);
		System.out.println( ByteUtil.toBinaryString(set.toByteArray()) );
		//System.out.println(set);
		for(long l:set.toLongArray()){
			System.out.println( ByteUtil.toBinaryString(l) );
		}
	}
}
