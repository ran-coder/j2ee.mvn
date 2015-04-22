package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CrmDateUtil {
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String	DATETIME_FORMAT			="yyyy-MM-dd HH:mm:ss";
	public static final String	TIGHT_FULL_DATE_FORMAT	="yyyy-MM-dd-HH-mm-ss";
	public static final String	TIME_FORMAT				="HH-mm-ss";
	public static final String	TIME_FORMAT2			="HHmmss";
	public static final String	TIME_FORMAT3			="h:mm aa";
	/**
	 * yyyy-MM-dd
	 */
	public static final String	DATE_FORMAT				="yyyy-MM-dd";

	/**
	 * yyyyMMdd
	 */
	public static final String	DATE_SINGLE_FORMAT_VER1	="yyyyMMdd";

	/**
	 * MM/dd/yyyy
	 */
	public static final String	DATE_SINGLE_FORMAT_VER2	="MM/dd/yyyy";

	/**
	 * date to string
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		if(date == null){ return ""; }
		String result="";
		DateFormat dateFormat=new SimpleDateFormat(DATETIME_FORMAT);
		result=dateFormat.format(date);
		return result;
	}

	/**
	 * string to date
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date, String format) {
		if(date == null){ return ""; }
		if(date == null || format == null){
			format=DATETIME_FORMAT;
		}
		String result="";
		DateFormat dateFormat=new SimpleDateFormat(format,Locale.ENGLISH);
		result=dateFormat.format(date);
		return result;
	}

	public static String date2String(java.sql.Timestamp date) {
		if(date == null){ return ""; }
		String result="";
		DateFormat dateFormat=new SimpleDateFormat(DATETIME_FORMAT);
		result=dateFormat.format(date);
		return result;
	}

	/**
	 * string to date
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(java.sql.Timestamp date, String format) {
		if(date == null)return null;
		if(date == null || format == null){
			format=DATETIME_FORMAT;
		}
		String result="";
		DateFormat dateFormat=new SimpleDateFormat(format);
		result=dateFormat.format(date);
		return result;
	}

	/**
	 * @param s
	 * @return
	 */
	public static Date string2Date(String s) {
		if(s == null || s.equals("")){ return null; }
		Date result=null;
		try{
			DateFormat format=null;
			if(s.length() > 15){
				format=new SimpleDateFormat(DATETIME_FORMAT);
			}else{
				format=new SimpleDateFormat(DATE_FORMAT);
			}
			result=format.parse(s);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public static Date string2Date(String s, String format) {
		Date result=null;
		if(format == null || format.equals("")){
			format=DATETIME_FORMAT;
		}
		try{
			DateFormat mFormat=new SimpleDateFormat(format,Locale.ENGLISH);
			result=mFormat.parse(s);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * next
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextDate(Date date) {
		if(date == null){ return null; }
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + 1);
		return calendar.getTime();
	}

	/**
	 * next date
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextDate(String dateStr) {
		if(dateStr == null || dateStr.equals("")){ return null; }
		Date date=string2Date(dateStr);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + 1);
		return calendar.getTime();
	}

	/**
	 * nextndate
	 * 
	 * @param dateStr
	 * @param n
	 * @return
	 */
	public static Date nextNDate(String dateStr, int n) {
		if(dateStr == null || dateStr.equals("")){ return null; }
		Date date=string2Date(dateStr);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + n);
		return calendar.getTime();
	}

	/**
	 * nextndate
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date nextNDate(Date date, int n) {
		if(date == null){ return null; }
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + n);
		return calendar.getTime();
	}

	/**
	 * nextnMin
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date nextNMin(Date date, int n) {
		if(date == null){ return null; }
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + n);
		return calendar.getTime();
	}

	public static Date nextNSecond(Date date, int n) {
		if(date == null){ return null; }
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND,calendar.get(Calendar.SECOND) + n);
		return calendar.getTime();
	}

	/**
	 * betweenDays
	 * 
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static int getBetweenDays(Object starttime, Object endtime) {
		Date start=null, end=null;
		if(starttime.getClass().equals(String.class)){
			start=CrmDateUtil.string2Date((String)starttime,CrmDateUtil.DATETIME_FORMAT);
		}else if(starttime.getClass().equals(Date.class)){
			start=(Date)starttime;
		}else{
			start=new Date();
		}
		if(endtime.getClass().equals(String.class)){
			end=CrmDateUtil.string2Date((String)endtime,CrmDateUtil.DATETIME_FORMAT);
		}else if(endtime.getClass().equals(Date.class)){
			end=(Date)endtime;
		}else{
			end=new Date();
		}
		Long between=end.getTime() - start.getTime();
		double days=between.doubleValue() / 86400000;// (1000*60*60*24)
		return Double.valueOf(Math.floor(days)).intValue();
	}

	/**
	 * return first day of this month
	 * 
	 * @param date
	 * @return
	 */
	public static String firstDayOfThisMonth(Date date) {
		String firstDayOfThisMonth=CrmDateUtil.date2String(date,CrmDateUtil.DATE_SINGLE_FORMAT_VER2);
		String[] dateArray=firstDayOfThisMonth.split("/");
		return dateArray[0] + "/01/" + dateArray[2];
	}

	/**
	 * return last day of this month
	 * 
	 * @param date
	 * @return
	 */
	public static String lastDayOfThisMonth(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH,(cal.get(Calendar.MONTH) + 1));
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.DAY_OF_MONTH,0);
		return CrmDateUtil.date2String(cal.getTime(),CrmDateUtil.DATE_SINGLE_FORMAT_VER2);
	}

	private static Log		log					=LogFactory.getLog(CrmDateUtil.class);
	private static String	defaultDatePattern	=null;
	private static String	timePattern			="HH:mm";

	//~ Methods ================================================================

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		defaultDatePattern=DATE_SINGLE_FORMAT_VER2;
		return defaultDatePattern;
	}

	public static String getDateTimePattern() {
		return CrmDateUtil.getDatePattern() + " HH:mm:ss.S";
	}

	/**
	 * This method attempts to convert an Oracle-formatted date
	 * in the form dd-MMM-yyyy to mm/dd/yyyy.
	 *
	 * @param aDate date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df=null;
		String returnValue="";

		if(aDate != null){
			df=new SimpleDateFormat(getDatePattern());
			returnValue=df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time
	 * in the format you specify on input
	 *
	 * @param aMask the date pattern the string is in
	 * @param strDate a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df=null;
		Date date=null;
		df=new SimpleDateFormat(aMask);

		if(log.isDebugEnabled()){
			log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}

		try{
			date=df.parse(strDate);
		}catch(ParseException pe){
			//log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(),pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format:
	 * MM/dd/yyyy HH:MM a
	 *
	 * @param theTime the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern,theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString=df.format(today);
		Calendar cal=new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time
	 * in the format you specify on input
	 *
	 * @param aMask the date pattern the string is in
	 * @param aDate a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df=null;
		String returnValue="";

		if(aDate == null){
			log.error("aDate is null!");
		}else{
			df=new SimpleDateFormat(aMask);
			returnValue=df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based
	 * on the System Property 'dateFormat'
	 * in the format you specify on input
	 * 
	 * @param aDate A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(),aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate=null;

		try{
			if(log.isDebugEnabled()){
				log.debug("converting date with pattern: " + getDatePattern());
			}

			aDate=convertStringToDate(getDatePattern(),strDate);
		}catch(ParseException pe){
			log.error("Could not convert '" + strDate + "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(),pe.getErrorOffset());

		}

		return aDate;
	}

	public static List<Date> getDatesFromStrings(String source) {
		if(source == null || "".equals(source)){
			return null;
		}else{
			String[] dates=source.split("_");
			ArrayList<Date> newdates=new ArrayList<Date>();

			for(int i=0;i < dates.length;i++){
				Date tempdate=nextDate(string2Date(dates[i],TIGHT_FULL_DATE_FORMAT));
				newdates.add(tempdate);
			}
			return newdates;
		}

	}

	public static int getRealAge(Date current, Date birthday) {
		Calendar ca1=Calendar.getInstance();
		Calendar ca2=Calendar.getInstance();
		ca1.setTime(current);
		ca2.setTime(birthday);
		int ca1Year=ca1.get(Calendar.YEAR);
		int ca1Month=ca1.get(Calendar.MONTH);

		int ca2Year=ca2.get(Calendar.YEAR);
		int ca2Month=ca2.get(Calendar.MONTH);
		int countMonth=0;
		if(ca1Year == ca2Year){
			countMonth=ca2Month - ca1Month;
		}else{
			countMonth=(ca2Year - ca1Year) * 12 + (ca2Month - ca1Month);
		}
		return countMonth % 12;
	}

	public static int getBookyAge(Date current, Date birthday) {
		Calendar ca1=Calendar.getInstance();
		Calendar ca2=Calendar.getInstance();
		ca1.setTime(current);
		ca2.setTime(birthday);
		int ca1Year=ca1.get(Calendar.YEAR);
		int ca1Month=ca1.get(Calendar.MONTH);
		Calendar date1=Calendar.getInstance();
		Calendar date2=Calendar.getInstance();
		Calendar date3=Calendar.getInstance();
		Calendar date4=Calendar.getInstance();
		Calendar date5=Calendar.getInstance();
		Calendar date6=Calendar.getInstance();
		Calendar date7=Calendar.getInstance();

		if(ca1Month >= 8){
			date1.set(ca1Year,8,1);
			date2.set(ca1Year - 1,8,1);
			date3.set(ca1Year - 2,8,1);
			date4.set(ca1Year - 3,8,1);
			date5.set(ca1Year - 4,8,1);
			date6.set(ca1Year - 5,8,1);
			date7.set(ca1Year - 6,8,1);
		}else{
			date1.set(ca1Year - 1,8,1);
			date2.set(ca1Year - 2,8,1);
			date3.set(ca1Year - 3,8,1);
			date4.set(ca1Year - 4,8,1);
			date5.set(ca1Year - 5,8,1);
			date6.set(ca1Year - 6,8,1);
			date6.set(ca1Year - 7,8,1);
		}

		if(ca2.after(ca1)){
			return -1;
		}else if(ca2.after(date1)){
			return 0;
		}else if(ca2.after(date2) && ca2.before(date1)){
			return 0;
		}else if(ca2.after(date3) && ca2.before(date2)){
			return 1;
		}else if(ca2.after(date4) && ca2.before(date3)){
			return 2;
		}else if(ca2.after(date5) && ca2.before(date4)){
			return 3;
		}else if(ca2.after(date6) && ca2.before(date5)){
			return 4;
		}else if(ca2.after(date7) && ca2.before(date6)){
			return 5;
		}else{
			return 10;
		}

	}

	public static boolean generateNDate2oneString(String source, int n) {
		StringBuffer sb=new StringBuffer();
		Date current=new Date();
		if(source == null || "".equals(source)){
			sb.append(date2String(current,TIGHT_FULL_DATE_FORMAT));
		}else{
			String[] dates=source.split("_");
			ArrayList<Date> newdates=new ArrayList<Date>();

			for(int i=0;i < dates.length;i++){
				Date tempdate=nextDate(string2Date(dates[i],TIGHT_FULL_DATE_FORMAT));
				if(tempdate.after(current)){
					newdates.add(tempdate);
				}
			}
			for(int i=0;i < newdates.size();i++){
				if(i == 0) sb.append(date2String(newdates.get(i),TIGHT_FULL_DATE_FORMAT));
				else sb.append("_").append(date2String(newdates.get(i),TIGHT_FULL_DATE_FORMAT));
			}
			if(newdates.size() >= n){
				//source=sb.toString();
				return false;
			}else{
				sb.append("_").append(date2String(current,TIGHT_FULL_DATE_FORMAT));
			}
		}
		//source=sb.toString();
		return true;
	}
	/**
	 * test
	 * @param args
	 * @throws ParseException 
	 */

}
