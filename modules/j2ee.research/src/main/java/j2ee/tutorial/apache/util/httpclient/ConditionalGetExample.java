package j2ee.tutorial.apache.util.httpclient;

import java.io.IOException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class ConditionalGetExample {
	public static void main(String[] args) throws HttpException, IOException {
		ConditionalGetExample example=new ConditionalGetExample();
		example.start();
	}

	String	entityTag		="";
	String	lastModified	="";

	public void start() throws HttpException, IOException {
		HttpClient client=new HttpClient();
		HttpMethod method=new GetMethod("http://www.apache.org");
		for(int i=0;i < 3;i++){
			setHeaders(method);
			client.executeMethod(method);
			processResults(method);
			method.releaseConnection();
		}
	}

	private void setHeaders(HttpMethod method) {
		method.setRequestHeader(new Header("If-None-Match",entityTag));
		method.setRequestHeader(new Header("If-Modified-Since",lastModified));
	}

	private void processResults(HttpMethod method) throws HttpException {
		if(method.getStatusCode() == HttpStatus.SC_NOT_MODIFIED){
			System.out.println("Content not modified since last request");
		}else{
			entityTag=retrieveHeader(method,"ETag");
			lastModified=retrieveHeader(method,"Last-Modified");
			System.out.println("Get Method retrieved content.");
			System.out.println("Entity Tag: " + entityTag);
			System.out.println("Last Modified: " + lastModified);
		}
	}

	private String retrieveHeader(HttpMethod method, String name) throws HttpException {
		HeaderElement[] header=method.getResponseHeader("ETag").getElements();
		String value="";
		if(header.length > 0){
			value=header[0].getName();
		}
		return value;
	}
}
