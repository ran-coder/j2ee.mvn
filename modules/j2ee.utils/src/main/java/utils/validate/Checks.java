package utils.validate;

import java.util.Collection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2014-01-09 16:35
 * To change this template use File | Settings | File Templates.
 */
public class Checks{
	/** if(array==null)return -1; else return array.length;   */
	public static int getLength(Object[] array){
		if(array==null)return -1;
		return array.length;
	}
	public static <T> boolean isEmptyArray(T[] array){
		return array==null||array.length<1;
	}
	//boolean[] byte[] char[] short[] int[] long[] double[] float[]
	/*数据类型	大小		范围										默认值
	boolean		1			true false									false
	byte		8			-128 127(-2^7 - 2^7-1)						0x0
	char		16			0x0000 0xffff								0x0
	short		16			-2^15 2^15-1								0x0
	int			32			0x80000000 0x7fffffff(-2^31 2^31-1)			0x0
	float		32														0.0f
	long		64			-2^63 2^63-1								0
	double		64														0.0d*/
	public static boolean isEmpty(boolean[] array){return array==null||array.length<1;}
	public static boolean isEmpty(byte[] array){return array==null||array.length<1;}
	public static boolean isEmpty(char[] array){return array==null||array.length<1;}
	public static boolean isEmpty(short[] array){return array==null||array.length<1;}
	public static boolean isEmpty(int[] array){return array==null||array.length<1;}
	public static boolean isEmpty(float[] array){return array==null||array.length<1;}
	public static boolean isEmpty(long[] array){return array==null||array.length<1;}
	public static boolean isEmpty(double[] array){return array==null||array.length<1;}

	public static <T> boolean isEmptyCollection(Collection<T> collection){
		return collection==null||collection.isEmpty();
	}
	public static <K,V> boolean isEmptyMap(Map<K,V> map){
		return map==null || map.isEmpty();
	}

	public static boolean isEmpty(String src){
		return src==null ||src.trim().length()<1;
	}

	/**
	 * srcs 有空的就返回true
	 * @param srcs
	 * @return
	 */
	public static boolean isEmptyAny(boolean defaultWhenIsEmptyArray,String... srcs){
		if(isEmptyArray(srcs))return defaultWhenIsEmptyArray;
		for(String src:srcs){
			if(isEmpty(src))return true;
		}
		return false;
	}
	/**
	 * srcs 是数组的时候不是空的,srcs 中有空的就返回true
	 * @param srcs
	 * @return
	 */
	public static boolean isEmptyAny(String... srcs){
		return isEmptyAny(true,srcs);
	}

	public static boolean isNull(Object obj){
		return null==obj;
	}

	public static int in(String src, String[] dest) {
		for (int i = 0; i < dest.length; i++) {
			if (src.equals(dest[i])) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Match if one character equals to any of the given character.
	 *
	 * @return <code>true</code> if characters match any character from given array,
	 *         otherwise <code>false</code>
	 */
	public static boolean in(char c, char[] match) {
		for (char aMatch : match) {
			if (c == aMatch) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Compares string with at least one from the provided array, ignoring case.
	 * If at least one equal string is found, it returns its index.
	 * Otherwise, <code>-1</code> is returned.
	 */
	public static int inIgnoreCase(String src, String[] dest) {
		for (int i = 0; i < dest.length; i++) {
			if (src.equalsIgnoreCase(dest[i])) {
				return i;
			}
		}
		return -1;
	}
}
