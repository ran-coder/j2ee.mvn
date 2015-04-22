package tutorial.apache.util;

/**

 */
import java.text.*;
import java.util.*;

/**
 * 时间工具类
 * <ul>
 * 	<li><code>getNow()</code>, <br>获得系统当前时间(yyyy-MM-dd HH:mm:ss).</li>
 *	<li><code>format(Date date)</code>, <br>格式化时间成字符串,非法返回null.</li>
 *  <li><code>format(Date date,String format)</code>, 格式化时间成字符串,非法返回null.</li>
 * </ul>
 * 
 * @author yuanwei
 */
public class DateUtil {
	public static void sleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}
	}

	public static void sleep(String str, long sleepTime) {
		try {
			System.out.println(str);
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 获得系统当前时间 格式:yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getNow()	{
		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
	}
	public static String now()	{
		return getNow();
	}
	
	/**
	 * 格式化时间成字符串,非法返回null
	 * @param date Date
	 * @return String(yyyy-MM-dd HH:mm:ss) 
	 */
	public static String format(Date date) {
		try {
			return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 格式化时间成字符串,非法返回null
	 * @param date Date
	 * @param format String 时间格式
	 * 		format=date yyyy-MM-dd
	 * 		format=time HH:mm:ss
	 * 		format=all yyyy-MM-dd HH:mm:ss 默认
	 * @return String
	 */
	public static String format(Date date,String format) {
		try {
			format=getFormat(format);
			return new java.text.SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 格式化时间成字符串,非法返回null,等于format(Date date,String format)
	 * @param date Date
	 * @param format String 时间格式
	 * 		format=date yyyy-MM-dd
	 * 		format=time HH:mm:ss
	 * 		format=all yyyy-MM-dd HH:mm:ss 默认
	 * @return String
	 */
	public static String getString(Date date,String format) {
		return format(date, format);
	}
	
	/**
	 * @param date String 格式:yyyy-MM-dd HH:mm:ss
	 * @return String 获得日期 yyyy-MM-dd
	 */
	public static String getDateString(String date) {
		try {
			return new java.text.SimpleDateFormat("yyyy-MM-dd").format(getDate(date,"all"));
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * @param date String 格式:yyyy-MM-dd HH:mm:ss
	 * @return String 获得时间 HH:mm:ss
	 */
	public static String getTimeString(String date) {
		try {
			return new java.text.SimpleDateFormat("HH:mm:ss").format(getDate(date,"all"));
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @return Date 当前时间 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getDate() {
		try {
			return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getNow());
		} catch (ParseException e) {
			System.out.println("日期转换失败:"+e);
			return null;
		}
	}
	
	/**
	 * @param date String(yyyy-MM-dd HH:mm:ss)
	 * @return Date 非法null
	 */
	public static Date getDate(String date) {
		String[] formats=new String[]{
				"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd",
				"yyyy/MM/dd HH:mm:ss","yyyy/MM/dd",
		};
		for (int i = 0,j=formats.length; i<j; i++) {
			try {
				return new java.text.SimpleDateFormat(formats[i]).parse(date);
			} catch (Exception e) {}
		}
		return null;
	}
	/**
	 * @param date String
	 * @param format String date=yyyy-MM-dd,time=HH:mm:ss,all及默认yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getDate(String date,String format) {
		try {
			format=getFormat(format);
			return new java.text.SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			//System.out.println("getDate日期转换失败:"+e);
			return null;
		}
	}
	
	
	/**
	 * @param date String(yyyy-MM-dd HH:mm:ss)
	 * @return boolean 判断是否是符合要求的时间格式,date为日常时间
	 */
	public static boolean checkDate(String date) {
		return date.equals(format(getDate(date)));
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
		if(getDate(date,format)==null)return false;
		return date.equals(format(getDate(date,format),format));
	}
	/**
	 * @param format String 
	 * @return String 返回格式 <br>date=yyyy-MM-dd,<br>time=HH:mm:ss,<br>all及默认yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormat(String format) {
		if(StringUtil.isEmpty(format) || format.equals("all"))
			format="yyyy-MM-dd HH:mm:ss";
		if(format.equals("date"))format="yyyy-MM-dd";
		if(format.equals("time"))format="HH:mm:ss";
		return format;
	}
	 /**
	  * 获取日期d的days天后的一个Date
	  * @param d
	  * @param days
	  * @return
	  */
	public static Date getDateByDay(Date d, int days){
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.DATE, days);
		return now.getTime();
	}

	public static Date getDateByMonth(Date d, int months){
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.MONTH, months);
		return now.getTime();
	}

	/**
	 * @param d 
	 * @param days
	 * @return 多少天后的日期
	 */
	public static String getDateAfterToday(int days,String format){
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(new java.util.Date());
		now.add(Calendar.DATE, days);
		if(StringUtil.isEmpty(format))format="yyyy-MM-dd HH:mm:ss";
		try {
			return new java.text.SimpleDateFormat(format).format(now.getTime());
		} catch (Exception e) {
			return "";
		}
	}
	 public static void main(String[] args) {
		//System.out.println(getNow());
		//System.out.println(getDate("8888-08-08 08:08:08").toLocaleString());
		//System.out.println(getDate("2008-12-01","yyyy-MM-dd").toLocaleString());
		//System.out.println(format(getDate("2008-12-01","yyyy-MM-dd"),"yyyy-MM-dd"));
		System.out.println(getString(getDate("888888-08-08 08:08:08","all"),"all"));
		System.out.println(checkDate("8888-08-08 00:00:00",""));
		
		System.out.println(format(getDateByDay(new java.util.Date(),-30)));
		System.out.println(getDate("2008-12-12 08:08:08"));
		System.out.println(getDate("2008-12-12"));
		System.out.println(getDate("2008/12/12 08:08:08"));
		System.out.println(getDate("2008/12/12"));
	}
}

