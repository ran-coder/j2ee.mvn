package j2ee.tutorial.apache.util.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;



public class SSLTest {

	public static void selfSigned(){
		/*HttpClient client=new HttpClient();
		String url="https://pericles.symbiont.net/jccook";

		ProtocolSocketFactory socketFactory=new EasySSLProtocolSocketFactory();
		Protocol https=new Protocol("https",socketFactory,443);
		Protocol.registerProtocol("https",https);

		HttpMethod method=new GetMethod(url);
		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(response);
		method.releaseConnection();*/
	}
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client=new HttpClient();
		String url="https://www.amazon.com/gp/flex/sign-in.html";
		url="https://www.google.com/reader/view/#stream/feed%2Fhttp%3A%2F%2Fwww.izaobao.us%2F%3Ffeed%3Drss2";
		HttpMethod method=new GetMethod(url);
		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(response);
		method.releaseConnection();

		
	}

}
