package utils;

import java.text.NumberFormat;
import java.util.Arrays;

public class Numbers {
	public static String doubleRound(double value, int decimalPlaces) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		//nf.setRoundingMode(RoundingMode.HALF_UP); // 四舍五入
		nf.setMaximumFractionDigits(decimalPlaces);
		nf.setMinimumFractionDigits(decimalPlaces);
		nf.setGroupingUsed(false);
		//System.out.println(":"+nf.format(value));
		return nf.format(value);
	}
	public static Integer parseInt(String str,Integer defaultValue){
		try{return Integer.valueOf(str.split("\\.")[0]);}catch(Exception e){}
		return defaultValue;
	}
	public static Double parseDouble(String str,Double defaultValue){
		try{
			return Double.valueOf(str);
		}catch(Exception e){}
		return defaultValue;
	}
	public static Long parseLong(String str,Long defaultValue){
		try{
			return Long.valueOf(str.split("\\.")[0]);
		}catch(Exception e){
			//log.error("RequestUtil.parseLong(str,defaultValue) error:str="+str+"	Exception:"+e.toString());
		}
		return defaultValue;
	}

	public static boolean eq(Long num,long l){
		return num!=null&&num.longValue()==l;
	}
	public static boolean eq(Integer num,int l){
		return num!=null&&num.intValue()==l;
	}
	/** num!=null && (num>=start && num<=end) */
	public static boolean between(Integer num,int start,int end){
		return num!=null && (num>=start && num<=end);
	}
	public static boolean between(Long num,long start,long end){
		return num!=null && (num>=start && num<=end);
	}
	public static boolean in(Integer num,int... is){
		if(num==null||is==null||is.length<1)return false;
		Arrays.sort(is);
		//System.out.println(Arrays.binarySearch(is,num));
		return Arrays.binarySearch(is,num)>-1;
	}
	public static boolean in(Long num,long... ls){
		if(num==null||ls==null||ls.length<1)return false;
		Arrays.sort(ls);
		return Arrays.binarySearch(ls,num)>-1;
	}

	public static boolean notIn(Integer num,int... is){
		return !in(num,is);
	}
	public static boolean notIn(Long num,long... ls){
		return !in(num,ls);
	}
	static boolean isNumber(Object value){
		boolean result=false;
		if(value==null||value.getClass()==null){
			result=false;
		}else if(Number.class.equals(value.getClass().getSuperclass())){
			result=true;
		}else if(String.class.equals(value.getClass())){
			result=false;
			//TODO
		}

		return result;
	}
	public static boolean gt(Object value,long gt){
		return value.getClass().getSuperclass().equals(Number.class) && ((Number)value).longValue()>gt;
	}
	public static <T> T setDefault(T t,T dv){
		return t==null?dv:t;
	}
	/** 相加 */
	public static double add(Double d1,Double d2){
		if(d1==null)d1=0D;
		if(d2==null)d2=0D;
		return d1+d2;
	}
	/** 相加 */
	public static double sum(Double... ds){
		double result=0D;
		if(ds==null||ds.length<1)return result;
		for(Double dd:ds){
			if(dd!=null)result+=dd;
		}
		return result;
	}
	/** 相减 */
	public static double subtract(Double d1,Double d2){
		if(d1==null)d1=0D;
		if(d2==null)d2=0D;
		return d1-d2;
	}
	/** 相减 */
	public static double sub(Double base,Double... ds){
		double result= (base==null?0D:base);
		if(ds==null||ds.length<1)return result;
		for(Double dd:ds){
			if(dd!=null)result-=dd;
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println(parseInt("4.0000",0));
		System.out.println(parseLong("100000000000.99",0L));
		System.out.println(eq((Long)null,125));
		System.out.println(eq(Long.valueOf(125),125));
		System.out.println(eq((Integer)null,125));
		System.out.println(eq(Integer.valueOf(125),125));
		System.out.println("****************** isNumber ******************");
		System.out.println(isNumber((byte)100));
		System.out.println(isNumber((short)100));
		System.out.println(isNumber(100D));
		System.out.println(isNumber(100));
		System.out.println(isNumber(100L));
		System.out.println(isNumber(Integer.valueOf(125)));
		System.out.println(isNumber(Long.valueOf(125)));
		System.out.println(isNumber(Byte.valueOf((byte)0x11)));
		System.out.println(isNumber(""));
	}
}
