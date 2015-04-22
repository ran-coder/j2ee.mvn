package utils.validate;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2014-01-10 14:22
 * To change this template use File | Settings | File Templates.
 */
public class Empty{
	public static final Object[] NULL_OBJECT_ARRAY;
	//boolean[] byte[] char[] short[] int[] long[] double[] float[]
	public static final boolean[] NULL_PRIMITIVE_ARRAY_BOOLEAN;
	public static final byte[] NULL_PRIMITIVE_ARRAY_BYTE;
	public static final char[] NULL_PRIMITIVE_ARRAY_CHAR;
	public static final short[] NULL_PRIMITIVE_ARRAY_SHORT;
	public static final int[] NULL_PRIMITIVE_ARRAY_INT;
	public static final long[] NULL_PRIMITIVE_ARRAY_LONG;
	public static final double[] NULL_PRIMITIVE_ARRAY_DOUBLE;
	public static final float[] NULL_PRIMITIVE_ARRAY_FLOAT;

	public static final List<?> NULL_LIST =null;
	public static final Collection<?> NULL_COLLECTION =null;
	public static final String NULL_STRING =null;
	public static final Object NULL_OBJECT =null;


	public static final Object[] EMPTY_ARRAY_OBJECT= new Object[0];
	public static final Class[] EMPTY_ARRAY_CLASS= new Class[0];
	public static final String[] EMPTY_ARRAY_STRING= new String[0];
	public static final Long[] EMPTY_ARRAY_LONG_OBJECT= new Long[0];
	public static final Integer[] EMPTY_ARRAY_INTEGER_OBJECT= new Integer[0];
	public static final Short[] EMPTY_ARRAY_SHORT_OBJECT= new Short[0];
	public static final Byte[] EMPTY_ARRAY_BYTE_OBJECT= new Byte[0];
	public static final Double[] EMPTY_ARRAY_DOUBLE_OBJECT= new Double[0];
	public static final Float[] EMPTY_ARRAY_FLOAT_OBJECT= new Float[0];
	public static final Boolean[] EMPTY_ARRAY_BOOLEAN_OBJECT= new Boolean[0];
	public static final Character[] EMPTY_ARRAY_CHARACTER_OBJECT= new Character[0];

	//boolean[] byte[] char[] short[] int[] long[] double[] float[]
	public static final boolean[] EMPTY_PRIMITIVE_ARRAY_BOOLEAN=new boolean[0];
	public static final byte[] EMPTY_PRIMITIVE_ARRAY_BYTE=new byte[0];
	public static final char[] EMPTY_PRIMITIVE_ARRAY_CHAR=new char[0];
	public static final short[] EMPTY_PRIMITIVE_ARRAY_SHORT=new short[0];
	public static final int[] EMPTY_PRIMITIVE_ARRAY_INT=new int[0];
	public static final long[] EMPTY_PRIMITIVE_ARRAY_LONG=new long[0];
	public static final double[] EMPTY_PRIMITIVE_ARRAY_DOUBLE=new double[0];
	public static final float[] EMPTY_PRIMITIVE_ARRAY_FLOAT=new float[0];

	static{
		NULL_OBJECT_ARRAY =(Object[])null;
		NULL_PRIMITIVE_ARRAY_BOOLEAN =null;
		NULL_PRIMITIVE_ARRAY_BYTE =null;
		NULL_PRIMITIVE_ARRAY_CHAR =null;
		NULL_PRIMITIVE_ARRAY_SHORT =null;
		NULL_PRIMITIVE_ARRAY_INT =null;
		NULL_PRIMITIVE_ARRAY_LONG =null;
		NULL_PRIMITIVE_ARRAY_DOUBLE =null;
		NULL_PRIMITIVE_ARRAY_FLOAT =null;
	}

	public static Object[] nullObjects(){
		return new Object[]{
				NULL_OBJECT_ARRAY,
				NULL_PRIMITIVE_ARRAY_BOOLEAN,
				NULL_PRIMITIVE_ARRAY_BYTE,
				NULL_PRIMITIVE_ARRAY_CHAR,
				NULL_PRIMITIVE_ARRAY_SHORT,
				NULL_PRIMITIVE_ARRAY_INT,
				NULL_PRIMITIVE_ARRAY_LONG,
				NULL_PRIMITIVE_ARRAY_DOUBLE,
				NULL_PRIMITIVE_ARRAY_FLOAT,
				NULL_LIST,
				NULL_COLLECTION,
				NULL_STRING,
				NULL_OBJECT
		};
	}
	public static Set<Object[]> emptyObjectArray(){
		Set<Object[]> set=new HashSet<Object[]>();
		set.add(EMPTY_ARRAY_OBJECT);
		set.add(EMPTY_ARRAY_CLASS);
		set.add(EMPTY_ARRAY_STRING);
		set.add(EMPTY_ARRAY_LONG_OBJECT);
		set.add(EMPTY_ARRAY_INTEGER_OBJECT);
		set.add(EMPTY_ARRAY_SHORT_OBJECT);
		set.add(EMPTY_ARRAY_BYTE_OBJECT);
		set.add(EMPTY_ARRAY_DOUBLE_OBJECT);
		set.add(EMPTY_ARRAY_FLOAT_OBJECT);
		set.add(EMPTY_ARRAY_BOOLEAN_OBJECT);
		set.add(EMPTY_ARRAY_CHARACTER_OBJECT);

		return set;
	}
}
