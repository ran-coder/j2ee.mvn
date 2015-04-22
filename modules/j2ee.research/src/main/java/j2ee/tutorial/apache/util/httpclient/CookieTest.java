package j2ee.tutorial.apache.util.httpclient;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import utils.DateUtil;

public class CookieTest {
	private static void makeRequest(HttpClient client) throws IOException, HttpException {
		String url="http://www.discursive.com/cgi-bin/jccook/cookie_test.cgi";
		HttpMethod method=new GetMethod(url);
		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(response);
		method.releaseConnection();
	}
	public static void testAddCookie() throws HttpException, IOException{
		HttpClient client=new HttpClient();

		System.out.println("Making Request without Cookie: ");
		makeRequest(client);

		System.out.println("Making Request with Cookie: ");
		Cookie cookie=new Cookie(".discursive.com","test_cookie","hello","/",null,false);
		client.getState().addCookie(cookie);
		makeRequest(client);
	}
	public static void test248() throws HttpException, IOException{
		HttpClient client=new HttpClient();
		client.getState().addCookie(
				new Cookie("192.168.39.248","JSESSIONID","146222231558D283BD5AB3480C40B2D9","/",DateUtil.string2Date("2015-12-7 15:04:11","yyyy-MM-dd HH:mm:ss"),false));
		client.getState().addCookie(
				new Cookie("192.168.39.248","ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE","YWRtaW46MTI5MTg3Nzk1MTc2MTo5OGVhY2YzZjZlNjIyYTZiZjBlYTRkOTkzMzZhN2Q1Mg==","/crm",DateUtil.string2Date("2015-12-7 15:04:11"),false));


		String url="http://192.168.39.248:81/crm/mainMenu.html";
		HttpMethod method=new GetMethod(url);
		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(StringUtils.substringBetween(response,"<title>","</title>"));
		method.releaseConnection();
	}
	public static void test115() throws HttpException, IOException{
		HttpClient client=new HttpClient();
		Date expire=DateUtil.string2Date("2015-12-7 15:04:11","yyyy-MM-dd HH:mm:ss");
		client.getState().addCookie(new Cookie(".115.com","LACK1","252BWU84WjcoOkZ7KHNZZExSZ2R6VEogSk94aFRadEZfZTs9QUw4YykmSXtsejFVV1AxcWwsQU1NMDVDTVNDI1EhKlN0NWk7YDVoSV5ZaWRIIyx7JT9uLzpUN0pKX1M1PVJFI14","/",expire,false));
		client.getState().addCookie(new Cookie(".115.com","LCCK","kyukoi","/",expire,false));
		client.getState().addCookie(new Cookie("u.115.com","PHPSESSID","fe2db9ad8f636dec0295b4a5b296937c","/",expire,false));
		client.getState().addCookie(new Cookie("u.115.com","citydata","101010100%2C%2C%2C%u5317%u4EAC%2C%u5317%u4EAC","/",expire,false));


		String url="http://u.115.com/?ac=my#ct=frame";
		HttpMethod method=new GetMethod(url);
		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(StringUtils.substringBetween(response,"<body>","</body>"));
		method.releaseConnection();
	}

	public static void main(String[] args) throws Exception {
		test115();

	}
}
