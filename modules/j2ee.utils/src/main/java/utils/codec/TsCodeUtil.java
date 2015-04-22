/**
 * 
 */
package utils.codec;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * @author yuanwei
 *
 */
public class TsCodeUtil {

	
	/**
	 * 将中文字符转换成国际码
	 * @param text
	 * @return
	 */
	public static String encode(String text) {
		if(text==null || text.length()<1) 
			return null;
		byte[] bs=null;
		try{
			bs=text.getBytes("GBK");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			return null;
		}
		int len=bs.length;
		byte[] rs=new byte[len*2];
		int temp=0;
		int count=0;
		for (int i = 0; i < len; i++) {
			//1:1 2:3 3:7 4:f 5:1f 6:3f 7:7f 8:ff
			switch (i%3) {
			case 0:{// 8765 4321 --> 0 0087 6543
				temp=((bs[i]&0xff)>>2);//8765 4321 --> 0087 6543
				rs[count++]=(byte)(temp+33);
				////rs[count++]=(byte)( ((bs[i]&0xff)>>2) +33)
				if(i==(len-1)) {
					temp=((bs[i]&0x3)<<4);
					rs[count++]=(byte)(temp+33);//8765 4321 --> 0021 0000
					//rs[count++]=(byte)( ((bs[i]&0x3)<<4) +33)
				}
				break;
			}
			case 1:{//8765 4321 --> 0 0000 8765
				//前面的 0021 0000 + (8765 4321 --> 0 0000 8765)
				temp=( ((bs[i]&0xff)>>4) );//8765 4321 --> 0 0000 8765
				temp+=((bs[i-1]&0x3)<<4);//前面的 0021 0000
				rs[count++]=(byte)(temp+33);
				//rs[count++]=(byte)(( ((bs[i]&0xff)>>4) + ((bs[i-1]&0x3)<<4) )+33);
				if(i==(len-1)) {
					temp=((bs[i]&0xf)<<2);
					rs[count++]=(byte)(temp+33);//8765 4321 --> 0043 2100
					//rs[count++]=(byte)( ((bs[i]&0xf)<<2) +33);
				}
				break;
			}
			case 2:{//8765 4321 --> 0 0000 0087 0065 4321
				temp=( ((bs[i]&0xff)>>6)  );//8765 4321 --> 0000 0087
				temp+=((bs[i-1]&0xf)<<2);//前面的0043 2100
				
				rs[count++]=(byte)(temp+33);
				//rs[count++]=(byte)( ( ((bs[i]&0xff)>>6) +((bs[i-1]&0xf)<<2) )+33);
				temp=(bs[i]&0x3f);//0065 4321
				rs[count++]=(byte)((bs[i]&0x3f)+33);
				//rs[count++]=(byte)(temp+33);
				break;
			}
			
			default:
				break;
			}
		}
		//print(text.getBytes());
		//print(rs);
		//System.out.println(new String(rs));
		String str=new String(rs);
		//if(str==null)return null;
		
		return "{"+str.trim()+"}";
		
	}
	
	
	/**
	 * 将国际码转换成中文字符
	 * @param text
	 * @return
	 */
	public static String decode(String text) {
		//Pattern.compile("^{.*}$")
		//Pattern.compile("^{.*}$").matcher(text).matches();//正则表达式表示
		if(text==null || text.length()<3)
			return null;
		text=text.substring(1, text.length()-1);
		byte[] rs;
		try {
			rs=text.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		if(rs==null || rs.length<1)return null;
		int len=rs.length;
		int count=0;
		byte[] bs=new byte[len*2];
		int temp=0;
		for(int i=0;i<len;i++){
			rs[i]=(byte)(((rs[i]-33)&0x3f));//-33 变成6位
		}
		for (int i = 0; i < len; i++) {
			switch (i%4) {
			case 0:{//6 
				temp=((rs[i]&0xff)<<2);//0012 3456 --> 1234 5600
				//bs[count++]=(byte)( (rs[i]&0xff)<<2 );
				if(i!=(len-1)) {
					temp+=((rs[i+1]&0xff)>>4);//取前二位 0012 3456 --> 0000 0012 
				}
				bs[count++]+=temp;
				//bs[count++]=(byte)( ((rs[i]&0xff)<<2) + ((rs[i+1]&0xff)>>4) );
				break;
			}case 1:{//2 4 
				temp=((rs[i]&0xff)<<4);//0012 3456 -->3456 0000
				//bs[count++]=(byte)( ((rs[i]&0xff)<<4) );
				if(i!=(len-1)) {
					temp+=((rs[i+1]&0xff)>>2);//// 0012 3456 -->0000 1234
				}
				bs[count++]+=temp;
				//bs[count++]=(byte)( ((rs[i]&0xff)<<4) + ((rs[i+1]&0xff)>>2) );
				break;
			}case 2:{//4 2
				temp=((rs[i]&0xff)<<6);//0012 3456 -->5600 0000
				//bs[count++]=(byte)( ((rs[i]&0xff)<<6) );
				if(i!=(len-1)) {
					temp+=((rs[i+1]&0xff));//// 0012 3456 -->0012 3456
				}
				bs[count++]+=temp;
				////bs[count++]=(byte)( ((rs[i]&0xff)<<6) + ((rs[i+1]&0xff)) );
				break;
			}case 3:{//6 
				break;
			}

			default:
				break;
			}
		}
		//print(bs);
		//bs=new byte[count];
		//print(bs);
		
		//String str=new String(bs);
		//if(str==null)return null;
		
		return new String(bs).trim();
	}
	
	public static void print(int i) {
		String temp=Integer.toBinaryString(i);
		
		switch (temp.length()) {
		case 1:
			System.out.print("0000000"+temp);
			break;
		case 2:
			System.out.print("000000"+temp);
			break;
		case 3:
			System.out.print("00000"+temp);
			break;
		case 4:
			System.out.print("0000"+temp);
			break;
		case 5:
			System.out.print("000"+temp);
			break;
		case 6:
			System.out.print("00"+temp);
			break;
		case 7:
			System.out.print("0"+temp);
			break;
		
		default:
			System.out.print(temp);
			break;
		}
		System.out.print(":"+i);
		System.out.println();
	}
	
	public static void print(byte b) {
		String temp=Integer.toString(b,2);
		//String temp=Integer.toBinaryString(b);
		//String temp=Byte.toString(b);
		//System.out.print(b+":");
		switch (temp.length()) {
		case 1:
			System.out.print("0000000"+temp);
			break;
		case 2:
			System.out.print("000000"+temp);
			break;
		case 3:
			System.out.print("00000"+temp);
			break;
		case 4:
			System.out.print("0000"+temp);
			break;
		case 5:
			System.out.print("000"+temp);
			break;
		case 6:
			System.out.print("00"+temp);
			break;
		case 7:
			System.out.print("0"+temp);
			break;
		case 8:
			System.out.print(temp);
			break;
		default:
			System.out.print(temp);
			break;
		}
		System.out.print(":"+b);
		System.out.println();
	}
	public static void print(byte[] bs) {
		if(bs==null || bs.length<1)return;
		String temp="";
		for (int i = 0,j=bs.length; i < j; i++) {
			byte b = bs[i];
			temp=Integer.toBinaryString((int)b&0xff);
			switch (temp.length()) {
			case 1:
				System.out.print("0000000"+temp);
				break;
			case 2:
				System.out.print("000000"+temp);
				break;
			case 3:
				System.out.print("00000"+temp);
				break;
			case 4:
				System.out.print("0000"+temp);
				break;
			case 5:
				System.out.print("000"+temp);
				break;
			case 6:
				System.out.print("00"+temp);
				break;
			case 7:
				System.out.print("0"+temp);
				break;
			case 8:
				System.out.print(temp);
				break;
			default:
				break;
			}
			System.out.print(" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws Exception {
		String text="{S]\\2XLP5}";//宋艳辉
		//String src="电视上看麦啊xxx克风都没离开vc";
		//String text="{U?\\0`L;M}";//缪毅
		/*int a=0x21;
		print(0x3f);
		print(a);
		print(a&0x3f);
		print((a&0x3f)<<2);
		decode("");*/
		//print(text.getBytes());
		System.out.println(Base64.encodeBase64String("宋艳辉".getBytes("GBK")));
		System.out.println(encode("宋艳辉"));;
		System.out.println(decode(text));;
		//decode("{S]}");
		//System.out.println(encode(src));
		//System.out.println(decode(encode(src)));
		//System.out.println(decode(text));
		//System.out.println(Pattern.compile("^{.*}$").matcher(text).matches());
		//print("宋艳辉".getBytes());
		//System.out.println("------------");
		//print("\\".getBytes());
		//System.out.println(text.getBytes().length);
		//System.out.println(src.getBytes().length);
		//print(src.getBytes());
		//print(text.getBytes());
		//11100111 11010001 11010010 11100011 
		//01011010 01011110 00101000 00110011 01011001 01010001
		byte b=-21;
		//print(21);
		//print(21<<4);
		print(b);
		print(b>>3);
		print(b>>>3);
	}

}
