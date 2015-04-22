package utils.validate;

import java.util.Collection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-20 11:18
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class Validators{
	/** if(array==null)return -1; else return array.length;   */
	public static int getLength(Object[] array){
		if(array==null)return -1;
		return array.length;
	}
	public static boolean isNullOrEmpty(Object[] array){
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
	public static boolean isNullOrEmpty(boolean[] array){return array==null||array.length<1;}
	public static boolean isNullOrEmpty(byte[] array){return array==null||array.length<1;}
	public static boolean isNullOrEmpty(char[] array){return array==null||array.length<1;}
	public static boolean isNullOrEmpty(short[] array){return array==null||array.length<1;}
	public static boolean isNullOrEmpty(int[] array){return array==null||array.length<1;}
	public static boolean isNullOrEmpty(float[] array){return array==null||array.length<1;}
	public static boolean isNullOrEmpty(long[] array){return array==null||array.length<1;}
	public static boolean isNullOrEmpty(double[] array){return array==null||array.length<1;}

	public static <T> boolean isNullOrEmpty(Collection<T> collection){
		return collection==null||collection.isEmpty();
	}
	public static <K,V> boolean isNullOrEmpty(Map<K,V> map){
		return map==null || map.isEmpty();
	}

	public static boolean isNullOrEmpty(String src){
		return src==null ||(src=src.trim()).length()<1;
	}

	public static boolean isNull(Object obj){
		return null==obj;
	}
}
