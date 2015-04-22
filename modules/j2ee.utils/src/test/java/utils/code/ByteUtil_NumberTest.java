package utils.code;

import static org.junit.Assert.*;
import static utils.codec.ByteUtil.*;
import static utils.codec.ByteUtil.Number.*;

import org.junit.Test;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-9-6 上午9:59:02
 *   
 */
public class ByteUtil_NumberTest {

	@Test
	public  void testInts2Bytes() {
		assertEquals("0x01,0x02,0x09,0x1f,0xff",toHexString(ints2Bytes(new int[]{0x01,0x02,0x09,0x1f,0xff},true)) );
		assertEquals("0x01,0x02,0x09,0x1f,0xff,0xf5",toHexString(ints2Bytes(new int[]{0x01,0x02,0x09,0x1f,0xff,0xfff5},true)) );
		assertEquals("0x01,0xff",toHexString(ints2Bytes(new int[]{1,0x0fffff},true)) );
		assertEquals("0xff,0x01",toHexString(ints2Bytes(new int[]{0x0fffff,1},true)) );

		assertEquals("0x01,0x02,0x09,0x1f,0xff,0xff,0xff",toHexString(ints2Bytes(new int[]{0x01,0x02,0x09,0x1f,0xff,0xffff},false)) );
		assertEquals("0x01,0xff",toHexString(ints2Bytes(new int[]{1,0xff},false)) );
		assertEquals("0x01,0x0f,0xff",toHexString(ints2Bytes(new int[]{1,0x0fff},false)) );
		assertEquals("0x01,0x7f,0xff,0xff,0xff",toHexString(ints2Bytes(new int[]{1,0x7fffffff},false)) );
		assertEquals("0x01,0x00,0x7f,0xff,0xff,0xff",toHexString(ints2Bytes(new int[]{1,0,0x7fffffff},false)) );
		assertEquals("0x01,0x00,0x7f,0xff,0xff,0xff,0x00",toHexString(ints2Bytes(new int[]{1,0,0x7fffffff,0x0},false)) );
		assertEquals("0x01,0x80,0x00,0x00,0x00",toHexString(ints2Bytes(new int[]{1,0x80000000},false)) );
		
		assertEquals("0xff,0x01",toHexString(ints2Bytes(new int[]{0xff,0x01},false)) );
		assertEquals("0x0f,0xff,0x01",toHexString(ints2Bytes(new int[]{0x0fff,0x01},false)) );
		assertEquals("0x7f,0xff,0xff,0xff,0x01",toHexString(ints2Bytes(new int[]{0x7fffffff,0x01},false)) );
		assertEquals("0x7f,0xff,0xff,0xff,0x01",toHexString(ints2Bytes(new int[]{0,0x7fffffff,0x01},false)) );
		assertEquals("0x80,0x00,0x00,0x00,0x01",toHexString(ints2Bytes(new int[]{0x80000000,0x01},false)) );
	}

	@Test
	public  void testBytes2Ints() {
		//fail("Not yet implemented");
		assertArrayEquals(new int[]{1,2,9,31,255},bytes2Ints(new byte[]{0x1,0x2,0x9,0x1f,(byte)0xff}));
	}

	@Test
	public  void testInt2Base64() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testLong2Base64() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testLong2Base64String() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testLong2Bytes() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testBytes2Long() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testInt2Bytes() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testBytes2Int() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testShort2Bytes() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testBytes2Short() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testBytes2IntLEByteArrayIntInt() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testBytes2IntLEByteArray() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testBytes2IntBEByteArrayIntInt() {
		//fail("Not yet implemented");
	}

	@Test
	public  void testBytes2IntBEByteArray() {
		//fail("Not yet implemented");
	}

}
