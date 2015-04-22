/**
 * 
 */
package utils.codec;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * @author yuanwei
 *
 */
public class Test {

	public static void test() throws UnsupportedEncodingException {
		String ss="用户名不能为空";
		byte[] uncode=ss.getBytes("Unicode");
		int x=0xff;
		StringBuilder result=new StringBuilder();
		for(int i=2;i < uncode.length;i++){
			if(i % 2 == 0) result.append("\\u");
			String abc=Integer.toHexString(x & uncode[i]);
			result.append(abc.replaceAll(" ","0"));
			//result += abc.format("%2s", abc).replaceAll(" ", "0");             
		}
		System.out.println(result);
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		//test();
		Scanner scanner=new Scanner(System.in);
		while(scanner.hasNextLine()){
			System.out.println(scanner.nextLine());
		}
	}

}
