package tutorial.apache.util.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientDebugTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws Exception {
		String logging="org.apache.commons.logging";
		// Configure Logging
		System.setProperty(logging + ".Log",logging + ".impl.SimpleLog");
		System.setProperty(logging + ".logging.simplelog.showdatetime","true");
		System.setProperty(logging + ".simplelog.log.httpclient.wire","debug");
		System.setProperty(logging + ".simplelog.log.org.apache.commons.httpclient","debug");

		HttpClient client=new HttpClient();
		String url="http://192.168.39.248:81/crm";
		HttpMethod method=new GetMethod(url);
		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(response);
		method.releaseConnection();
	}

}
