package j2ee.tutorial.apache.util.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;

public class BasicAuthenticationTest {

	/**
	 * @param args
	 * @throws java.io.IOException
	 * @throws HttpException 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client=new HttpClient();
		HttpState state=client.getState();

		// Set credentials on the client
		Credentials credentials=new UsernamePasswordCredentials("testuser","crazypass");
		state.setCredentials(null,null,credentials);
		String url="http://www.discursive.com/jccook/auth/";
		HttpMethod method=new GetMethod(url);

		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(response);
		method.releaseConnection();

		client=new HttpClient();
		Credentials credentials1=new NTCredentials("testuser","crazypass","homecomputer","TESTDOM");
		Credentials credentials2=new NTCredentials("anotheruser","password2","homecomputer","DIFFERENT_DOMAIN");
		state=client.getState();
		state.setCredentials(null,"webmail.domain.biz",credentials1);
		state.setCredentials(null,"silly-iis-server.lame.net",credentials2);
		// Execute a request which uses credentials1
		url="http://webmail.domain.biz/exchange/";
		method=new GetMethod(url);
		client.executeMethod(method);
		// Execute a request which uses credentials2
		String url2="http://silly-iis-server.lame.net/test/";
		HttpMethod method2=new GetMethod(url2);
		client.executeMethod(method2);

	}

}
