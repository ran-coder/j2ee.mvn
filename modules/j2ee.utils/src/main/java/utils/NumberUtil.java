package utils;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;

import org.apache.commons.lang.math.NumberUtils;


public class NumberUtil {
	
	/**
	 * 给double四舍五入
	 * @param value
	 * @param decimalPlaces 保留小数点位数
	 * @return
	 */
	public static double doubleRound(double value, int decimalPlaces) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		//nf.setRoundingMode(RoundingMode.HALF_UP); // 四舍五入
		nf.setMaximumFractionDigits(decimalPlaces);
		nf.setMinimumFractionDigits(decimalPlaces);
		nf.setGroupingUsed(false);
		//System.out.println(":"+nf.format(value));
		return Double.valueOf(nf.format(value));
	}
	public static String percent(int sum,int total){
		if(total==0)return "";
		java.text.NumberFormat nf=java.text.NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);
		return nf.format((double)sum / (double)total);
	}
	public static String rate(int sum,int total){
		if(total==0 || sum==0)return "0";
		java.text.NumberFormat nf=java.text.NumberFormat.getIntegerInstance();
		nf.setMinimumFractionDigits(2);
		return nf.format((double)sum / (double)total);
	}
	/**
	 * str.getBytes("utf-8") 数组转换成double[]
	 * @param str
	 * @return
	 */
	public static double[] toDoubles(String str){
		if(str==null)return null;
		byte[] bs=null;
		try{
			bs=str.getBytes("utf-8");
		}catch(UnsupportedEncodingException e){
			System.out.println("toDoubles Exception:"+e.toString());
		}
		if(bs!=null && bs.length>0){
			double[] result=new double[bs.length];
			for(int i=0,j=bs.length;i < j;i++){
				result[i]=(double)bs[i];
			}
			return result;
		}
		return null;
	}
	
	public static class Parser{
		public static long parseLong(String str, int radix,long defaultValue){
			if(str == null)return defaultValue;

			if(radix < Character.MIN_RADIX)return defaultValue;
			if(radix > Character.MAX_RADIX)return defaultValue;

			long result=0;
			boolean negative=false;
			int i=0, max=str.length();
			long limit;
			long multmin;
			int digit;

			if(max > 0){
				if(str.charAt(0) == '-'){
					negative=true;
					limit=Long.MIN_VALUE;
					i++;
				}else{
					limit=-Long.MAX_VALUE;
				}
				multmin=limit / radix;
				if(i < max){
					digit=Character.digit(str.charAt(i++),radix);
					if(digit < 0){
						return defaultValue;
					}else{
						result=-digit;
					}
				}
				while(i < max){
					// Accumulating negatively avoids surprises near MAX_VALUE
					digit=Character.digit(str.charAt(i++),radix);
					if(digit < 0)return defaultValue;
					if(result < multmin)return defaultValue;
					result*=radix;
					if(result < limit + digit)return defaultValue;
					result-=digit;
				}
			}else{
				return defaultValue;
			}
			if(negative){
				if(i > 1){
					return result;
				}else{ /* Only got "-" */
					return defaultValue;
				}
			}else{
				return -result;
			}
		}
		public static long parseLong(String str,long defaultValue){
			return parseLong(str,10,defaultValue);
		}
		public static long parseLongUseExcp(String str,long defaultValue){
			try{
				return Long.parseLong(str);
			}catch(Exception e){
				return defaultValue;
			}
		}
		
		public static byte parseByte(String s, int radix,byte defaultValue){
			int i=Integer.parseInt(s,radix);
			if(i < Byte.MIN_VALUE || i > Byte.MAX_VALUE) return defaultValue;
			return (byte)i;
		}
		
		public static int parseInt(String str, int radix,int defaultValue) throws NumberFormatException {
			if(str == null || str.trim().length()<1)return defaultValue;

			if(radix < Character.MIN_RADIX)return defaultValue;
			if(radix > Character.MAX_RADIX)return defaultValue;

			int result=0;
			boolean negative=false;
			int i=0, max=str.length();
			int limit;
			int multmin;
			int digit;

			if(max > 0){
				if(str.charAt(0) == '-'){
					negative=true;
					limit=Integer.MIN_VALUE;
					i++;
				}else{
					limit=-Integer.MAX_VALUE;
				}
				multmin=limit / radix;
				if(i < max){
					digit=Character.digit(str.charAt(i++),radix);
					if(digit < 0){
						return defaultValue;
					}else{
						result=-digit;
					}
				}
				while(i < max){
					// Accumulating negatively avoids surprises near MAX_VALUE
					digit=Character.digit(str.charAt(i++),radix);
					if(digit < 0)return defaultValue;
					if(result < multmin)return defaultValue;
					result*=radix;
					if(result < limit + digit)return defaultValue;
					result-=digit;
				}
			}else{
				return defaultValue;
			}
			if(negative){
				if(i > 1){
					return result;
				}else{ /* Only got "-" */
					return defaultValue;
				}
			}else{
				return -result;
			}
		}
	}

	public static void main(String[] args) {
		/*
		 * System.out.println(doubleRound(123.455, 2));//123.46
		 * System.out.println(doubleRound(123.449, 2));//123.45
		 * System.out.println(doubleRound(123.44, 3)); //123.440
		 * System.out.println(doubleRound(123.4455, 3));
		 * System.out.println(doubleRound(123.4499, 3));
		 * System.out.println(doubleRound(123.9, 0));
		 * System.out.println(doubleRound(123456.9, 0));
		 * System.out.println(doubleRound(123456.9, 1));
		 * System.out.println(doubleRound(123456.9, 2));
		 * System.out.println(percent(1,3)); System.out.println(rate(2,30));
		 * System.out.println(rate(6,3));
		 */
		
		/*System.out.println(NumberUtils.toInt("100",0));
		System.out.println(NumberUtils.toDouble("14646.1465456",0.00));
		System.out.println(NumberUtils.isNumber("100.22"));
		System.out.println(NumberUtils.isDigits("100"));//整数
		System.out.println(NumberUtils.compare(100,200));*/
		//System.out.println(parseLong("10",10,0L));
		long start=System.nanoTime();
		for(int i=0;i<100;i++){
			NumberUtils.toLong(i+"",0L);
		}
		System.out.println(System.nanoTime()-start);
		start=System.nanoTime();
		for(int i=0;i<100;i++){
			Parser.parseLongUseExcp(i+""+(i%2==0?i:"o"),0L);
		}
		System.out.println(System.nanoTime()-start);
		start=System.nanoTime();
		for(int i=0;i<100;i++){
			Parser.parseLong(i+""+(i%2==0?i:"o"),0L);
		}
		System.out.println(System.nanoTime()-start);
	}
	
}
