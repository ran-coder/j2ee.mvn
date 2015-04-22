package j2ee.tutorial.apache.util.lang;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

public class DateFormatUtilsTest {
	public static void main(String[] args) {
		System.out.println(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		System.out.println(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss:ms"));
		FastDateFormat formatter =FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss:ms");
		System.out.println(formatter.format(new Date()));

		FastDateFormat dtFormat = DateFormatUtils.ISO_DATETIME_FORMAT;
		Date now = new Date( );
		Date nearestHour = DateUtils.round( now, Calendar.HOUR );
		Date nearestDay = DateUtils.round( now, Calendar.DAY_OF_MONTH );
		Date nearestYear = DateUtils.round( now, Calendar.YEAR );
		System.out.println( "Now: " + dtFormat.format( now ) );
		System.out.println( "Nearest Hour: " + dtFormat.format( nearestHour ) );
		System.out.println( "Nearest Day: " + dtFormat.format( nearestDay ) );
		System.out.println( "Nearest Year: " + dtFormat.format( nearestYear ) );

	}
}
