package j2ee.research.java.reference;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-25 17:13
 * To change this template use File | Settings | File Templates.
 */
public class NativeVarExample{
	public static void change(char[] chars){
		chars[0]='A';
	}
	public static void change(char ch){
		ch='A';
	}

	public static void main(String[] args){
		//原始内型int char不会变,String 不会变
		char[] chars="123456".toCharArray();
		change(chars);System.out.println(Arrays.toString(chars));//会变
		char ch='1';change(ch);System.out.println(ch);//不会变
	}
}
