package tutorial.apache.util.lang;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class StringUtilsTest {
	public static void main(String[] args) {
		// data setup
		//String str1 = "";
		//String str2 = " ";
		//String str3 = "\t";
		//String str4 = null;
		//String str5 = "123";
		//String str6 = "ABCDEFG";
		//String str7 = "It feels good to use Jakarta Commons.\r\n";
		System.out.println("==============================");
		System.out.println(StringUtils.isBlank(""));//true
		System.out.println(StringUtils.isBlank(" "));//true
		System.out.println(StringUtils.isBlank("\t"));//true
		System.out.println(StringUtils.isBlank(null));//true

		System.out.println("==============================");
		System.out.println("numeric? " + StringUtils.isNumeric("123"));
		System.out.println("numeric? " + StringUtils.isNumeric("ABCDEFG"));
		// reverse strings / whole words
		System.out.println("==============================");
		//System.out.println("str6: " + str6);
		System.out.println("reversed: " + StringUtils.reverse("ABCDEFG"));
		//System.out.println("str7: " + "It feels good to use Jakarta Commons.\r\n");
		String str8=StringUtils.chomp("It feels good to use Jakarta Commons.\r\n");
		str8=StringUtils.reverseDelimited(str8,' ');
		System.out.println("str7 reversed whole words : \r\n" + str8);
		// build header (useful to print log messages that are easy to locate)
		System.out.println("==============================");
		System.out.println("print header:");
		String padding=StringUtils.repeat("=",50);
		String msg=StringUtils.center(" Customised Header ",50,"*");
		//System.out.println("msg:"+msg);
		Object[] raw=new Object[]{ padding, msg, padding };
		String header=StringUtils.join(raw,"\r\n");
		System.out.println(header);

		System.out.println(StringUtils.abbreviate("This is a test of the abbreviation.",10));
		System.out.println(StringUtils.abbreviate("原理很简单，关键是效率。以流媒体解码为例，一次读几比特处理完一个若干兆的文件",10));

		System.out.println(ArrayUtils.toString(StringUtils.split("Frantically oblong"," ",2)));
		System.out.println(ArrayUtils.toString(StringUtils.split("Pharmacy,basketball,funky",",",2)));

		String htmlContent="<html>\n" + "  <head>\n" + "    <title>Test Page</title>\n" + "  </head>\n" + "  <body>\n"
				+ "    <p>This is a TEST!</p>\n" + "  </body>\n" + "</html>";
		//Extract the title from this XHTML content
		System.out.println("Title: " + StringUtils.substringBetween(htmlContent,"<title>","</title>"));

		System.out.println("******************* StringUtils.substringBetween *******************");
		System.out.println(StringUtils.substringBetween( "|TESTING| BOUNDARYExampleBOUNDARY", "|"));
		System.out.println(StringUtils.substringBetween( "|TESTING| BOUNDARY-Example-BOUNDARY", "BOUNDARY"));

		System.out.println("******************* StringUtils.trim *******************");
		System.out.println( StringUtils.trim( " \\\\a\r Testing 1 2 3 " ) );
		System.out.println( StringUtils.trimToNull( " \r\n " ) );
		System.out.println( "Stripped: " + StringUtils.strip( "-------***---SHAZAM!---***-------", "-*" ) );
		System.out.println(StringUtils.chomp( "Hello\n" ));// chomped equals "Hello"
		System.out.println(StringUtils.chomp( "Another test\r\n" ));// chomped2 equals "Another test";

		System.out.println("******************* StringUtils.countMatches *******************");
		System.out.println(StringUtils.countMatches("100100100","0"));

		System.out.println("******************* String Difference *******************");
		System.out.println( "Edit Distance: " + StringUtils.getLevenshteinDistance( "Word", "World" ) );
		System.out.println( "Difference: " + StringUtils.difference( "Word", "World" ) );
		System.out.println( "Diff Index: " + StringUtils.indexOfDifference( "Word", "World" ) );
		String a="Strategy";
		String b="Strategic";
		System.out.println("difference(Strategy, Strategic) = " + StringUtils.difference(a,b));
		System.out.println("index(Strategy, Strategic) = " + StringUtils.indexOfDifference(a,b));
		a="The Secretary of the UN is Kofi Annan.";
		b="The Secretary of State is Colin Powell.";
		System.out.println("difference(..., ...) = " + StringUtils.difference(a,b));
		System.out.println("index(..., ...) = " + StringUtils.indexOfDifference(a,b));

	}
}
