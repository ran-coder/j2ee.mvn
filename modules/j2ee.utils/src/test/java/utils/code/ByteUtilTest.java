package utils.code;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static utils.codec.ByteUtil.*;

import java.util.Arrays;

import org.junit.Test;

import utils.codec.ByteUtil;
import utils.codec.CharUtil;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-8-31 下午4:39:48
 *   
 */
public class ByteUtilTest {

	@Test
	public void testToHexChar() {
		//fail("Not yet implemented");
	}

	@Test
	public void testToHexUpperChar() {
		//fail("Not yet implemented");
	}

	@Test
	public void testToBinaryStringIntInt() {
		//fail("Not yet implemented");
	}

	@Test
	public void testToBinaryStringInt() {
		//fail("Not yet implemented");
	}

	@Test
	public void testToBinaryString_ByteArray() {
		byte[] bytes=null;
		assertEquals(null,toBinaryString(bytes));
		bytes=new byte[]{0x00,0x01};
		assertEquals( "0000 0000,0000 0001",toBinaryString(bytes) );
		bytes=new byte[]{-1};
		assertEquals( "1111 1111",toBinaryString(bytes) );
		bytes=new byte[]{-1,-128};
		assertEquals( "1111 1111,1000 0000",toBinaryString(bytes) );
	}

	@Test
	public void testToBinaryStringLongInt() {
		//fail("Not yet implemented");
	}

	@Test
	public void testToBinaryStringLong() {
		//fail("Not yet implemented");
		int n=62;
		assertEquals("1111 1111,1111 1111,1111 1111,1111 1111	1111 1111,1111 1111,1111 1111,1111 1111",toBinaryString(-1L));
		assertEquals("0000 0000,0000 0000,0000 0000,0000 0000	0000 0000,0000 0000,0000 0000,0000 0011",toBinaryString(-1L >>> n));
		assertEquals("0011 1111,1111 1111,1111 1111,1111 1111	1111 1111,1111 1111,1111 1111,1111 1111",toBinaryString(-1L >>> -n));
	}

	@Test
	public void testTrim() {
		assertArrayEquals(new byte[]{0x64,0x56,0x66,0x56},trim(new byte[]{0x00,0x00,0x00,0x00,0x64,0x56,0x66,0x56}));
		assertArrayEquals(new byte[]{0x64,0x56,0x66,0x56},trim(new byte[]{0x00,0x00,0x64,0x56,0x66,0x56,0x00,0x00}));
	}
	@Test
	public void testSubBytes() {
		assertArrayEquals(new byte[]{},ByteUtil.subBytes(new byte[]{},1,1));
		assertArrayEquals(null,ByteUtil.subBytes(null,1,1));
		assertArrayEquals(new byte[]{0x00},ByteUtil.subBytes(new byte[]{0x00,0x00,0x00,0x00,0x64,0x56,0x66,0x56},1,1));
		assertArrayEquals(new byte[]{0x00,0x00},ByteUtil.subBytes(new byte[]{0x00,0x00,0x00,0x00,0x64,0x56,0x66,0x56},0,1));
		assertArrayEquals(new byte[]{0x00,0x00,0x00},ByteUtil.subBytes(new byte[]{0x00,0x00,0x00,0x00,0x64,0x56,0x66,0x56},0,2));
		assertArrayEquals(new byte[]{0x64,0x56,0x66},ByteUtil.subBytes(new byte[]{0x00,0x00,0x00,0x00,0x64,0x56,0x66,0x56},4,6));
		assertArrayEquals(new byte[]{0x64,0x56,0x66,0x56},ByteUtil.subBytes(new byte[]{0x00,0x00,0x00,0x00,0x64,0x56,0x66,0x56},4,-1));
	}
	@Test
	public void toBinaryString_Long_Int() {
		long l=100L;
		assertEquals("0110 0100",toBinaryString(l,8));
		assertEquals("0000 0000,0110 0100",toBinaryString(l,16));
		assertEquals("0000 0000,0000 0000,0110 0100",toBinaryString(l,24));
		assertEquals("0000 0000,0000 0000,0000 0000,0110 0100",toBinaryString(l,32));
		assertEquals("0000 0000,0000 0000,0000 0000,0000 0000\t0000 0000,0000 0000,0000 0000,0110 0100",toBinaryString(l,64));
		l=Long.MAX_VALUE;
		assertEquals("1111 1111",toBinaryString(l,8));
		assertEquals("1111 1111,1111 1111",toBinaryString(l,16));
		assertEquals("1111 1111,1111 1111,1111 1111",toBinaryString(l,24));
		assertEquals("1111 1111,1111 1111,1111 1111,1111 1111",toBinaryString(l,32));
		assertEquals("0111 1111,1111 1111,1111 1111,1111 1111	1111 1111,1111 1111,1111 1111,1111 1111",toBinaryString(l,64));
		assertEquals("0111 1111,1111 1111,1111 1111,1111 1111	1111 1111,1111 1111,1111 1111,1111 1111",toBinaryString(l));
	}
	@Test
	public void testChars2Bytes_Char(){
		assertEquals("0x4e,0x2d",toHexString(chars2Bytes('中')));
		assertEquals("0x00,0x61,0x00,0x62,0x00,0x63",toHexString(chars2Bytes('a','b','c')));
		assertEquals("0x00,0x61,0x00,0x62,0x00,0x63,0x4e,0x2d",toHexString(chars2Bytes('a','b','c','中')));
	}
	@Test
	public void testBytes2Chars(){
		assertEquals("0x4e2d",CharUtil.toHexString(bytes2Chars( new byte[]{0x4e,0x2d} )));
		assertEquals("0x4e2d,0x0001",CharUtil.toHexString(bytes2Chars( new byte[]{0x4e,0x2d,0x1} )));
		assertEquals("0x0061,0x0062,0x0063,0x4e2d",CharUtil.toHexString(bytes2Chars( new byte[]{0x00,0x61,0x00,0x62,0x00,0x63,0x4e,0x2d} )));
	}
	@Test
	public void testToHexString_Bytes(){
		assertEquals("0xff",toHexString(new byte[]{(byte)0xff}));
	}
	@Test
	public void testFixBytesLength() {
		byte[] bytes=new byte[]{-127,0x0,-127};
		assertEquals("0000 0000,1000 0001,0000 0000,1000 0001",toBinaryString(fixBytesLength(bytes,4)));
		assertEquals("1000 0001,0000 0000,1000 0001,0000 0000",toBinaryString(Arrays.copyOf(bytes,4)));
		assertEquals("1000 0001,0000 0000,1000 0001,0000 0000",toBinaryString(newBytes(bytes,4)));
		//Arrays.fill 不会改变byte[]大小
		Arrays.fill(bytes,0,1,(byte)0x0);
		assertEquals("0000 0000,0000 0000,1000 0001",toBinaryString(bytes));
	}
	@Test
	public void testToString_Bytes(){
		assertEquals("0,15,31,255",ByteUtil.toString(new byte[]{0x0,0xf,0x1f,(byte)0xff}));
	}
	@Test
	public void testInt2HexString_Int(){
		assertEquals("0xff",int2HexString(0xff));
		assertEquals("0x0f",int2HexString(0xf));
		assertEquals("0x0fff",int2HexString(0xfff));
		assertEquals("0x0fffff",int2HexString(0xfffff));
	}
	@Test
	public void testLong2HexString_Long(){
		
	}
	@Test
	public void toBinaryString_Int_String(){
		assertEquals("0000 0000,0000 0000,0000 0000,0001 0000",toBinaryString(16,null));
		assertEquals("0000 0000,0000 0000,0000 0000,0001 0000:16",toBinaryString(16,"16"));
		assertEquals("0000 0000,0000 0000,0000 0000,0000 0001:1",toBinaryString(1,"1"));
	}
}
