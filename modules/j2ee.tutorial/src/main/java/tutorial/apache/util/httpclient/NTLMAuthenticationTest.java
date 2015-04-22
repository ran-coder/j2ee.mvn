package tutorial.apache.util.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.methods.GetMethod;

public class NTLMAuthenticationTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client=new HttpClient();
		// Set credentials on the client
		Credentials credentials=new NTCredentials("testuser","crazypass","homecomputer ","TESTDOM");
		HttpState state=client.getState();
		state.setCredentials(null,null,credentials);
		String url="http://webmail.domain.biz/exchange/";
		HttpMethod method=new GetMethod(url);

		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(response);
		method.releaseConnection();

	}

}
