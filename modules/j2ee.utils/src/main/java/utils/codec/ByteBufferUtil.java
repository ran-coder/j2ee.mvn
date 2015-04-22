package utils.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-9-2 下午3:55:56
 *   
 */
public class ByteBufferUtil {
	public static void string2Char(String target,String charset){
		if(charset==null||charset.trim().length()<1)
			charset="UTF-8";
		Charset codecharset = Charset.forName(charset); 
		ByteBuffer byteBuffer = codecharset.encode(target);
		CharBuffer charBuffer = codecharset.decode(byteBuffer);
		System.out.println(ByteUtil.toBinaryString(byteBuffer.array()));
		System.out.println(ByteUtil.toBinaryString(charBuffer.array()));
	}

	public static void main(String[] args) {
		string2Char("中文","UTF-8");
		string2Char("中文","GBK");
	}
}
