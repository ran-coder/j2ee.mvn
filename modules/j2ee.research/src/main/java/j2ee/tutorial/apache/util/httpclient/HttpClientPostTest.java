package j2ee.tutorial.apache.util.httpclient;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

public class HttpClientPostTest {

	public static void postByParam() throws Exception {
		HttpClient client=new HttpClient();

		// Create POST method
		String url="http://www.discursive.com/cgi-bin/jccook/param_list.cgi";
		PostMethod method=new PostMethod(url);
		// Set parameters on POST
		method.setParameter("test1","Hello World");
		method.addParameter("test2","This is a Form Submission");
		method.addParameter("Blah","Whoop");
		method.addParameter(new NameValuePair("Blah","Whoop2"));
		NameValuePair[] data={ 
				new NameValuePair("user","joe"),
				new NameValuePair("password","bloggs")
		};
		method.setRequestBody(data);

		// Execute and print response
		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(response);
		method.releaseConnection();
	}

	public static void postByFile() throws Exception {
		HttpClient client=new HttpClient();

		// Create POST method
		String weblintURL="http://ats.nist.gov/cgi-bin/cgi.tcl/echo.cgi";
		PostMethod method=new PostMethod(weblintURL);
		File file=new File("project.xml");
		RequestEntity requestEntity=new FileRequestEntity(file,"text/plain; charset=UTF-8");

		method.setRequestEntity(requestEntity);
		//method.setRequestBody(new FileInputStream(file));
		//method.setRequestContentLength((int)file.length());
		// Execute and print response
		client.executeMethod(method);
		String response=method.getResponseBodyAsString();
		System.out.println(response);
		method.releaseConnection();

	}

	public static void main(String[] args) throws Exception {
		postByFile();
	}

}
