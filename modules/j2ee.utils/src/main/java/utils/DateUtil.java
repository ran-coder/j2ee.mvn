package utils;

/**

 */
import java.text.*;
import java.util.*;

import utils.test.benchmark.Benchmarks;
import utils.test.benchmark.Task;

/**
 * 时间工具类
 * 
 * @author yuanwei
 */
public class DateUtil {
	/** yyyy-MM-dd-HH-mm-ss */
	public static final String FULL_VER1				="yyyy-MM-dd-HH-mm-ss";
	/** yyyy-MM-dd HH:mm:ss */
	public static final String DEFAULT				="yyyy-MM-dd HH:mm:ss";
	/** yyyyMMddHHmmss */
	public static final String FULL_VER2				="yyyyMMddHHmmss";
	/** yyyyMMddHHmmssSS */
	public static final String FULL_VER3				="yyyyMMddHHmmssSS";
	/** HH:mm:ss */
	public static final String TIME					="HH:mm:ss";
	/** HH-mm-ss */
	public static final String TIME_VER1				="HH-mm-ss";
	/** HHmmss */
	public static final String TIME_VER2				="HHmmss";
	/** h:mm aa */
	public static final String TIME_VER3				="h:mm aa";
	/** yyyy-MM-dd */
	public static final String DATE					="yyyy-MM-dd";
	/** yyyyMMdd */
	public static final String DATE_VER1				="yyyyMMdd";
	/** MM/dd/yyyy */
	public static final String DATE_VER2				="MM/dd/yyyy";
	/** yyyy/MM/dd */
	public static final String DATE_VER3				="yyyy/MM/dd";

	/**
	 * 获得系统当前时间 格式:yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String now(){
		return toString(new Date(),DEFAULT);
	}
	/**
	 * 获得系统当前时间 格式:yyyy-MM-dd
	 * @return String
	 */
	public static String nowDate(){
		return toString(new Date(),DATE);
	}
	/**
	 * 获得系统当前时间 格式:yyyy-MM-dd
	 * @return String
	 */
	public static String nowDate(String format){
		return toString(new Date(),format);
	}
	/**
	 * 获得系统当前时间 格式:HH:mm:ss
	 * @return String
	 */
	public static String nowTime(){
		return toString(new Date(),TIME);
	}
	/**
	 * 格式化时间成字符串,异常返回null
	 * @param date Date
	 * @return String(yyyy-MM-dd HH:mm:ss) 
	 */
	public static String toString(Date date){
		return toString(date,DEFAULT);
	}
	
	/**
	 * 格式化时间成字符串,异常返回null
	 * @param date Date
	 * @param format String 时间格式
	 * 		format=date yyyy-MM-dd
	 * 		format=time HH:mm:ss
	 * 		format=all yyyy-MM-dd HH:mm:ss 默认
	 * @return String
	 */
	public static String toString(Date date,String format) {
		return toString(date,format,Locale.ENGLISH);
	}
	/**
	 * 格式化时间成字符串,异常返回null
	 * @param date Object
	 * @param format String 时间格式
	 * 		format=date yyyy-MM-dd
	 * 		format=time HH:mm:ss
	 * 		format=all yyyy-MM-dd HH:mm:ss 默认
	 * @return String
	 */
	public static String toString(Object date,String format) {
		return toString((Date)date,format,Locale.ENGLISH);
	}
	/**
	 * 格式化时间成字符串,异常返回null
	 * @param date Date
	 * @param format String 时间格式
	 * 		format=date yyyy-MM-dd
	 * 		format=time HH:mm:ss
	 * 		format=all yyyy-MM-dd HH:mm:ss 默认
	 * @return String
	 */
	public static String toString(Date date,String format,Locale locale) {
		if(date==null)return null;
		if(locale==null)locale=Locale.ENGLISH;

		try {
			format=getFormat(format);
			return new SimpleDateFormat(format,locale).format(date);
		} catch (Exception e) {
			return null;
		}
	}
	

	/**
	 * @param date String(yyyy-MM-dd HH:mm:ss)
	 * @return Date 异常null
	 */
	public static Date string2Date(String date) {
		return string2Date(date,DEFAULT);
	}
	/**
	 * @param date String
	 * @param format String date=yyyy-MM-dd,time=HH:mm:ss,all及默认yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date string2Date(String date,String format) {
		return string2Date(date,format,Locale.ENGLISH);
	}
	public static Date string2Date(String date,String format,Locale locale) {
		if(date==null)return null;
		if(locale==null)locale=Locale.ENGLISH;
		try {
			return new java.text.SimpleDateFormat(getFormat(format),locale).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	
	/**
	 * @param date String(yyyy-MM-dd HH:mm:ss)
	 * @return boolean 判断是否是符合要求的时间格式,date为日常时间
	 */
	public static boolean checkDate(String date) {
		return date.equals(toString(string2Date(date)));
	}

	/**
	 * @param date String
	 * @param format String
	 * @return boolean 判断date和format对应的时间格式是否相同,date为日常时间
	 */
	public static boolean checkDate(String date,String format) {
		format=getFormat(format);
		//System.out.println("data:"+date);
		if(StringUtil.isEmpty(date))return false;
		if(string2Date(date,format)==null)return false;
		return date.equals(toString(string2Date(date,format),format));
	}
	/**
	 * @param format String 
	 * @return String 返回格式 <br>date=yyyy-MM-dd,<br>time=HH:mm:ss,<br>all及默认yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormat(String format) {
		if(StringUtil.isEmpty(format) || format.equals("all"))
			format=DEFAULT;
		if(format.equals("date"))format=DATE;
		if(format.equals("time"))format=TIME;
		return format;
	}

	/** n可为负数 */
	public static Date nextDate(int n) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + n);
		return calendar.getTime();
	}
	/** n可为负数 */
	public static Date nextDate(Date date, int n) {
		if(date == null){ return null; }
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + n);
		return calendar.getTime();
	}
	/** n可为负数 */
	public static Date nextMinute(Date date, int n) {
		if(date == null){ return null; }
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + n);
		return calendar.getTime();
	}
	/** n可为负数 */
	public static Date nextNSecond(Date date, int n) {
		if(date == null){ return null; }
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND,calendar.get(Calendar.SECOND) + n);
		return calendar.getTime();
	}
	public static void testCalendar (){
		String format="yyyyMMdd";
		Calendar calendar=Calendar.getInstance();
		Calendar end=Calendar.getInstance();
		calendar.setTime(string2Date("20090717",format));
		end.setTime(string2Date("20100507",format));
		while(true){
			if(calendar.after(end))break;
			//System.out.println("webalizer -o \"D:/Apache/apache2215-crm/usage\" D:/Apache/apache2215-crm/logs/access_log_" +format(calendar.getTime(),format));
			System.out.println("webalizer -o \"D:/Apache/apache2215-ec/usage\" D:/Apache/apache2215-ec/logs/access_log_" +toString(calendar.getTime(),format));
			calendar.add(Calendar.DATE,1);
		}
	}
	public static long betweenDays(String end,String start){
		if(end==null||start==null)return 0L;
		return betweenDays(string2Date(end,DEFAULT),string2Date(start,DEFAULT));
	}
	public static long betweenDays(Date end,Date start){
		//todo
		/*
		Java统计从1970年1月1日起的毫秒的数量表示日期。也就是说，例如，1970年1月2日，是在1月1日后的86，400，000毫秒。同样的，1969年12月31日是在1970年1月1日前86，400，000毫秒。Java的Date类使用long类型纪录这些毫秒值.因为long是有符号整数，所以日期可以在1970年1月1日之前，也可以在这之后。Long类型表示的最大正值和最大负值可以轻松的表示290，000，000年的时间，这适合大多数人的时间要求。
		 */
		if(end==null||start==null)return 0L;
		return (end.getTime() - start.getTime()) / (24L * 60 * 60 * 1000);
	}
	public static int betweenNativeDays(Date start, Date end){
		int betweenDays = 0;
		if(start==null||end==null)return -1;
		Calendar startCalendar=Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar=Calendar.getInstance();
		endCalendar.setTime(end);
		// 保证第二个时间一定大于第一个时间
		if(startCalendar.after(endCalendar)){
			startCalendar.setTime(end);
			endCalendar.setTime(start);
		}
		int betweenYears = endCalendar.get(Calendar.YEAR)-startCalendar.get(Calendar.YEAR);
		betweenDays = endCalendar.get(Calendar.DAY_OF_YEAR)-startCalendar.get(Calendar.DAY_OF_YEAR);
		for(int i=0;i<betweenYears;i++){
			startCalendar.set(Calendar.YEAR,(startCalendar.get(Calendar.YEAR)+1));
			betweenDays += startCalendar.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays; 
	}
	public static boolean isBetween(Date target,Date start,Date end){
		if(target==null||start==null||end==null)return false;
		return betweenDays(target,start)>=0 && betweenDays(target,end)<=0;
	}
	public static boolean isBetween(String target,String start,String end){
		if(target==null||start==null||end==null)return false;
		return isBetween(string2Date(target,DEFAULT),string2Date(start,DEFAULT),string2Date(end,DEFAULT));
	}
	public static long getDateNumber(Date date){
		if(date==null)return Long.valueOf(0);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		//calendar.clear(Calendar.HOUR);
		//calendar.clear(Calendar.MINUTE);
		//calendar.clear(Calendar.SECOND);
		calendar.set(Calendar.HOUR,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		//calendar.get(Calendar.DAY_OF_YEAR);
		return calendar.getTimeInMillis();
		/*long times=date.getTime();
		return times-times%(3600L*1000*24);*/

	}
	public static int getDayOfYearNumber(Date date){
		if(date==null)return Integer.valueOf(0);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.get(Calendar.DAY_OF_YEAR);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}
	public static int getDayOfYearNumber(){
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.get(Calendar.DAY_OF_YEAR);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}
	public static long getCurrentDateNumber(){
		return getDateNumber(new Date());
	}
	public static void main(String[] args) {
		//testCalendar();
		//System.out.println(getNow());
		//System.out.println(getDate("8888-08-08 08:08:08").toLocaleString());
		//System.out.println(getDate("2008-12-01","yyyy-MM-dd").toLocaleString());
		//System.out.println(format(getDate("2008-12-01","yyyy-MM-dd"),"yyyy-MM-dd"));
		//System.out.println(getString(getDate("888888-08-08 08:08:08","all"),"all"));
		//System.out.println(checkDate("8888-08-08 00:00:00",""));
		
		//System.out.println(format(getDateByDay(new java.util.Date(),-30)));
		//System.out.println(getDate("2008-12-12 08:08:08"));
		//System.out.println(getDate("2008-12-12"));
		//System.out.println(getDate("2008/12/12 08:08:08"));
		//System.out.println(getDate("2008/12/12"));
		//System.out.println(DateUtil.format(DateUtil.getDate("02/Jul/2010:21:40:41 +0800","dd/MMM/yyyy:HH:mm:ss Z",Locale.US),"yyyy-MM-dd HH:mm:ss"));
		//System.out.println(format(new Date(),"dd/MMM/yyyy:HH:mm:ss Z",Locale.ENGLISH));
		System.out.println(betweenDays("2012-1-5 08:08:08","2011-1-5 00:00:00"));
		System.out.println(betweenDays("2012-1-5 23:00:00","2012-1-4 01:00:00"));
		System.out.println(isBetween("2012-1-5 23:00:00","2012-1-4 01:00:00","2012-1-5 23:00:00"));
		System.out.println(isBetween("2012-1-4 00:10:00","2012-1-4 01:00:00","2012-1-5 23:00:00"));
		System.out.println(isBetween("2012-1-3 00:00:00","2012-1-4 01:00:00","2012-1-5 23:00:00"));

		Benchmarks.runTask(1,1000,null,new Task("getCurrentDateNumber"){
			@Override
			public void run(){
				System.out.println(System.currentTimeMillis()+","+getDayOfYearNumber());
				//ClassUtil.sleep(1000L);
			}
		});


	}
}

