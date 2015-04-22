package utils.code;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import utils.Benchmark;
import utils.codec.CharUtil;

/**
 * @author yuanwei
 * @version ctreateTime:2011-8-24 下午5:16:04
 */
public class CharUtilTest {
	final static String target="<,.;\'\":|\\!@#$%^&*()_+-=`~?qwertyuiiopasdfghjklzxcvbnm/>load方法可以充分利用内部缓存和二级缓存中的现有数据，而get方法则仅仅在内部缓存中进行数据查找，如没有发现对应数据，将越过二级缓存，直接调用SQL完成数据读取";

	@Test
	public void testValue(){
		//System.out.println(UnicodeUtil.native2Ascii(target));
		System.out.println(CharUtil.native2Ascii(target,true));
		//System.out.println(UnicodeUtil.toUnicodeMapbar2006(target,false));
	}
	@Test
	public void testUrlEncode() throws Exception{
		String url="淘宝";
		System.out.println(CharUtil.urlEncode(url,"UTF-8"));
		System.out.println(URLEncoder.encode(url,"UTF-8"));
		System.out.println(CharUtil.urlEncode(url,"GBK"));
		System.out.println(URLEncoder.encode(url,"GBK"));
	}
	@Test
	public void base64(){
		byte[] bs=null;
		try{
			bs=target.getBytes("UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		System.out.println(bs==null?0:bs.length);
		//byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize)
		System.out.println(new String(Base64.encodeBase64(bs,false,true)));
		System.out.println(Base64.encodeBase64(bs,false,true).length);
	}
	@Test
	public void test() {
		Benchmark.runtime(10,200,true,"native2Ascii",new Runnable() {
			public void run() {
				CharUtil.native2Ascii(target);
			}
		});
		Benchmark.runtime(10,200,true,"toUnicodeMapbar2006",new Runnable() {
			public void run() {
				CharUtil.toUnicodeMapbar2006(target,false);
			}
		});
		Benchmark.runtime(10,200,true,"toUnicodeMapbar",new Runnable() {
			public void run() {
				CharUtil.toUnicodeMapbar(target);
			}
		});
		Benchmark.execute(100,200,true,new String[]{"native2Ascii","toUnicodeMapbar"},
			new Runnable() {
				public void run() {
					CharUtil.native2Ascii(target);
				}
			},new Runnable() {
				public void run() {
					CharUtil.toUnicodeMapbar2006(target,false);
				}
			}
		);
	}

}
