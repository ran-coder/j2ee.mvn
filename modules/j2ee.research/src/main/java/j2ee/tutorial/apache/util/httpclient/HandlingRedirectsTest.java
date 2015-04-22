package j2ee.tutorial.apache.util.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class HandlingRedirectsTest {
	private static void executeMethod(HttpClient client, HttpMethod method) throws IOException, HttpException {
		client.executeMethod(method);
		System.out.println("Response Code: " + method.getStatusCode());
		String response=method.getResponseBodyAsString();
		System.out.println(response.length());
		method.releaseConnection();
	}

	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client=new HttpClient();
		String url="http://192.168.39.248:81/crm";

		System.out.println("Executing Method not following redirects: ");
		HttpMethod method=new GetMethod(url);
		HttpClientParams params=client.getParams();
		params.setBooleanParameter(HttpClientParams.REJECT_RELATIVE_REDIRECT,false);
		params.setBooleanParameter(HttpClientParams.ALLOW_CIRCULAR_REDIRECTS,false);
		params.setIntParameter(HttpClientParams.MAX_REDIRECTS,10);

		method.setFollowRedirects(false);
		executeMethod(client,method);
		System.out.println("Executing Method following redirects: ");
		method=new GetMethod(url);
		method.setFollowRedirects(true);
		executeMethod(client,method);

	}
}
