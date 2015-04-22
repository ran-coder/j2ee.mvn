package utils.console;


import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import utils.DateUtil;
import utils.StringUtil;

public class InputUtil {

	private BufferedReader buf=null ;
	private Logger log=Logger.getLogger(this.getClass().getName());
	
	//获取屏幕输入的方法
	public InputUtil(){
		this.buf = new BufferedReader(new InputStreamReader(System.in)) ;
	}
	//获取输入字符串
	public String getString(){
		String str = "" ;
		try {
			//if(System.in.available()!=0)
			str = buf.readLine() ;
		} catch (IOException e) {
			//e.printStackTrace();
			log.error("输入错误!");
		}
		if(StringUtil.isEmpty(str)) {
			System.out.print("输入为空，请重新输入(exit返回):");
			return new InputUtil().getString();
		}
		//this.close();
		return str.trim() ;
	}
	public String getString(String format,int len){
		String str = "" ;
		try {
			//if(System.in.available()!=0)
			str = buf.readLine() ;
		} catch (IOException e) {
			//e.printStackTrace();
			log.error("输入错误!");
		}
		if(StringUtil.isEmpty(str)) {
			System.out.print("输入为空，请重新输入(exit返回):");
			return new InputUtil().getString(format,len);
		}
		if(str.equals("eixt"))return "";
		if(format.equals("c")) {//输入字母
			if(!Pattern.compile("[a-zA-Z]+").matcher(str).matches() 
					|| !StringUtil.checkLength(str,"", len)) {
				System.out.print("输入错误，必须为字母。\n请重新输入(exit返回):");
				return new InputUtil().getString("c",len);
			}
		}
		if(format.equals("n")) {//数字
			if(!Pattern.compile("[0-9]+").matcher(str).matches()) {
				System.out.print("输入错误，必须为数字。\n请重新输入(exit返回):");
				return new InputUtil().getString("c",len);
			}
		}
		if(format.equals("c+")) {//字母,(,)
			if(!Pattern.compile("[a-zA-Z\\(\\)]+").matcher(str).matches()) {
				System.out.print("输入错误，必须为字母、'('、')'。\n请重新输入(exit返回):");
				return new InputUtil().getString("c",len);
			}
		}
		if(format.equals("cn")) {//输入字母数字
			if(!Pattern.compile("\\w+").matcher(str).matches()) {
				System.out.print("输入错误，必须为字母、数字。\n请重新输入(exit返回):");
				return new InputUtil().getString("cn",len);
			}
		}
		if(format.equals("cn+")) {//输入字母数字()
			if(!Pattern.compile("[\\w\\(\\)]+").matcher(str).matches()) {
				System.out.print("输入错误，必须为字母、数字和括号。\n请重新输入(exit返回):");
				return new InputUtil().getString("cn+",len);
			}
		}
		if(format.equals("yyyy-mm-dd")) {//yyyy-mm-dd
			if(!DateUtil.checkDate(str,"yyyy-mm-dd")) {
				System.out.print("输入错误，日期格式必须为yyyy-mm-dd。\n请重新输入(exit返回):");
				return new InputUtil().getString("yyyy-mm-dd",len);
			}
		}
		if(format.equals("zh;c")) {//中文
			if(!Pattern.compile("[\u4e00-\u9fa5]+[;|；]{0,1}(equal|left|right|all){0,1}").matcher(str).matches()) {
				System.out.print("输入错误，必须为中文。\n请重新输入(exit返回):");
				return new InputUtil().getString("zh;c",len);
			}
		}
		if(format.equals("cn+;c")) {//由字母、数字和括号组成
			if(!Pattern.compile("[\\w\\(\\)]+;*[a-zA-Z]+").matcher(str).matches()) {
				System.out.print("输入错误，必须由字母、数字和括号组成。\n请重新输入(exit返回):");
				return new InputUtil().getString("cn+;c",len);
			}
		}
		if(format.equals("d;d")) {//yyyy-mm-dd hh:mm:ss;yyyy-mm-dd hh:mm:ss
			if(str.indexOf(";")!=-1) {//2008-12-30 12:00:00
				String[] ar=str.split(";");
				System.out.println("length:"+str.split(";").length);
				if(!DateUtil.checkDate(ar[0],"all") || (ar.length==2&&!DateUtil.checkDate(ar[1],"all"))) {
					System.out.print("输入错误，日期格式为yyyy-MM-dd hh:mm:ss。\n请重新输入(exit返回):");
					return new InputUtil().getString("d;d",len);
				}
			}else {
				if(!DateUtil.checkDate(str,"all")) {
					System.out.print("输入错误，日期格式为yyyy-MM-dd hh:mm:ss。\n请重新输入(exit返回):");
					return new InputUtil().getString("d;d",len);
				}
			}
		}
		return str.trim();
	}
	
	//获取输入整数
	public int getInt(){
		int temp = -1 ;
		String str = "" ;
		try {
			//if(System.in.available()!=0) 
			str = buf.readLine() ;
		} catch (IOException e1) {
			//e1.printStackTrace();
			log.error("输入错误!");
		}
		if(!("".equals(str))){
			try {
				temp = Integer.parseInt(str) ;
			} catch (NumberFormatException e) {
				System.out.println("请输入数字!");
				log.error(e.getMessage());
				//e.printStackTrace();
			}
		}else{
			temp = 0 ;
		}
		return temp ;
	}
	
	public void close(){
		try {
			System.in.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//new InputUtil().getString("zh;c",-1);
		//boolean b=Pattern.compile("[\u4e00-\u9fa5]+[;|；]{0,1}(equal|left|right|all){0,1}")	.matcher("大大大").matches();
		//b=!Pattern.compile("[a-zA-Z]+").matcher(str).matches() 
			//&& !StringUtil.checkLength(str, 3);
		//System.out.println(b);
		System.out.print("11111111111:");
		System.out.println(new InputUtil().getString());
	}
}
