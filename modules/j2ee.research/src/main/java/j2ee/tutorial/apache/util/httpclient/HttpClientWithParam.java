package j2ee.tutorial.apache.util.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;

public class HttpClientWithParam {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		HttpClient client=new HttpClient();
		String url="http://192.168.39.248:81/";
		HttpMethod method=new GetMethod(url);
		// Set the Query String with setQueryString( )
		method.setQueryString(URIUtil.encodeQuery("test1=O Reilly&blah=Whoop"));
		NameValuePair pair=new NameValuePair("test1",URIUtil.encodeQuery("O Reilly"));
		NameValuePair pair2=new NameValuePair("blah",URIUtil.encodeQuery("Whoop"));
		NameValuePair[] pairs=new NameValuePair[]{ pair, pair2 };
		method.setQueryString(pairs);

		System.out.println("With Query String: " + method.getURI());
		client.executeMethod(method);
		System.out.println("Response:\n " + method.getResponseBodyAsString());
		method.releaseConnection();

	}

}
