package tutorial.apache.util.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class MultiThreadedTest {
	public static void main(String[] args) throws HttpException, IOException {
		MultiThreadedHttpConnectionManager connectionManager=new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams managerParams=connectionManager.getParams();
		//managerParams.setMaxConnectionsPerHost(new HostConfiguration(),100);
		managerParams.setMaxTotalConnections(10);

		HttpClient client=new HttpClient(connectionManager);

		// and then from inside some thread executing a method
		GetMethod get=new GetMethod("http://httpcomponents.apache.org/");
		try{
			client.executeMethod(get);
			// print response to stdout
			System.out.println(get.getResponseBodyAsStream());
		}finally{
			// be sure the connection is released back to the connection
			// manager
			get.releaseConnection();
		}

	}
}
