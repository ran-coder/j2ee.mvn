package utils.validate;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2014-01-09 16:42
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;
import static utils.validate.Checks.*;


public class ChecksTest{
	@Before
	public void init(){

	}
	@Test
	public void testGetLength() {
		assertTrue(getLength( Empty.NULL_OBJECT_ARRAY )==-1);
		assertTrue(getLength(new String[]{"1","2"})==2);
		assertTrue(getLength(new String[]{})==0);
		assertTrue(getLength(new Long[]{})==0);
		assertTrue(getLength(new Integer[]{})==0);

	}

	@Test
	public void testIsEmptyArray_Object() {
		for(Object[] objs:Empty.emptyObjectArray()){
			assertTrue(isEmptyArray(objs));
		}
		assertTrue(isEmptyArray(Empty.NULL_OBJECT_ARRAY));
		assertTrue(isEmptyArray(new Long[]{1L,2L})==false);
		assertTrue(isEmptyArray(new Integer[]{1,2})==false);
	}

	@Test
	public void testIsEmpty_PrimitiveArray() {
		//boolean[] byte[] char[] short[] int[] long[] double[] float[]
		/*assertTrue(isEmpty((boolean[])null));
		assertTrue(isEmpty((byte[])null));
		assertTrue(isEmpty((char[])null));
		assertTrue(isEmpty((short[])null));
		assertTrue(isEmpty((int[])null));
		assertTrue(isEmpty((long[])null));
		assertTrue(isEmpty((double[])null));
		assertTrue(isEmpty((float[])null));

		assertTrue(isEmpty(new boolean[]{}));
		assertTrue(isEmpty(new byte[]{}));
		assertTrue(isEmpty(new char[]{}));
		assertTrue(isEmpty(new short[]{}));
		assertTrue(isEmpty(new int[]{}));
		assertTrue(isEmpty(new long[]{}));
		assertTrue(isEmpty(new double[]{}));
		assertTrue(isEmpty(new float[]{}));*/

		assertTrue(isEmpty(Empty.NULL_PRIMITIVE_ARRAY_BOOLEAN ));
		assertTrue(isEmpty(Empty.NULL_PRIMITIVE_ARRAY_BYTE ));
		assertTrue(isEmpty(Empty.NULL_PRIMITIVE_ARRAY_CHAR ));
		assertTrue(isEmpty(Empty.NULL_PRIMITIVE_ARRAY_SHORT ));
		assertTrue(isEmpty(Empty.NULL_PRIMITIVE_ARRAY_INT ));
		assertTrue(isEmpty(Empty.NULL_PRIMITIVE_ARRAY_LONG ));
		assertTrue(isEmpty(Empty.NULL_PRIMITIVE_ARRAY_DOUBLE ));
		assertTrue(isEmpty(Empty.NULL_PRIMITIVE_ARRAY_FLOAT ));
		assertTrue(isEmpty(Empty.EMPTY_PRIMITIVE_ARRAY_BOOLEAN));
		assertTrue(isEmpty(Empty.EMPTY_PRIMITIVE_ARRAY_BYTE));
		assertTrue(isEmpty(Empty.EMPTY_PRIMITIVE_ARRAY_CHAR));
		assertTrue(isEmpty(Empty.EMPTY_PRIMITIVE_ARRAY_SHORT));
		assertTrue(isEmpty(Empty.EMPTY_PRIMITIVE_ARRAY_INT));
		assertTrue(isEmpty(Empty.EMPTY_PRIMITIVE_ARRAY_LONG));
		assertTrue(isEmpty(Empty.EMPTY_PRIMITIVE_ARRAY_DOUBLE));
		assertTrue(isEmpty(Empty.EMPTY_PRIMITIVE_ARRAY_FLOAT));

		assertTrue(isEmpty(new boolean[]{true})==false);
		assertTrue(isEmpty(new byte[]{0x1})==false);
		assertTrue(isEmpty(new char[]{0x1})==false);
		assertTrue(isEmpty(new short[]{0x1})==false);
		assertTrue(isEmpty(new int[]{0x1})==false);
		assertTrue(isEmpty(new long[]{0x1})==false);
		assertTrue(isEmpty(new double[]{0x1})==false);
		assertTrue(isEmpty(new float[]{0x1})==false);

	}



	@Test
	public void testIsEmptyCollection() {

		assertTrue( isEmptyCollection(Empty.NULL_LIST)  );
		assertTrue( isEmptyCollection(Empty.NULL_COLLECTION)  );
		assertTrue( isEmptyCollection(Collections.emptySet())  );
		assertTrue( isEmptyCollection(Collections.emptyList())  );
		assertTrue( isEmptyCollection(new ArrayList<Object>())  );
		assertTrue( isEmptyCollection(new ArrayList<Object>(){{add(Integer.valueOf(1));add(Long.valueOf(1));}})==false  );
	}

	@Test
	public void testIsEmptyMap() {
		assertTrue( isEmptyMap(Collections.emptyMap())  );
		Map<Object,Object> map=null;
		assertTrue( isEmptyMap(map)  );
		map=new HashMap<Object, Object>();		assertTrue( isEmptyMap(map)  );
		map.put(Integer.valueOf(1),"value");		assertTrue( isEmptyMap(map)==false  );
		map.put(Long.valueOf(1),"value");		assertTrue( map.size()==2  );
	}

	@Test
	public void testIsEmptyString() {
		assertTrue( isEmpty((String)null)  );
		assertTrue( isEmpty("")  );
		assertTrue( isEmpty("\t\t\t")  );
		assertTrue( isEmpty("11 ")==false  );
	}


	@Test
	public void testIsEmptyAny() {
		assertTrue( isEmptyAny(true)==true  );
		assertTrue( isEmptyAny(false)==false  );
		String[] array=null;
		assertTrue( isEmptyAny(false,array)==false  );
		assertTrue( isEmptyAny(true,array)==true  );
		String[] array1={};
		assertTrue( isEmptyAny(true,array1)==true  );
		assertTrue( isEmptyAny(false,array1)==false  );

		assertTrue( isEmptyAny(true,"1","2")==false  );
		assertTrue( isEmptyAny(true,"1","","2")==true  );
		assertTrue( isEmptyAny(true,"1",null,"2")==true  );

		assertTrue( isEmptyAny()==true  );
		assertTrue( isEmptyAny("1","2")==false  );
		assertTrue( isEmptyAny("1","","2")==true  );
		assertTrue( isEmptyAny("1",null,"2")==true  );
	}
	@Test
	public void testIsNull() {
		assertTrue( isNull((Integer)null)  );
		assertTrue( isNull((Long)null)  );

		for(Object obj:Empty.nullObjects()){
			assertTrue( isNull( obj ) );
		}
	}

}

