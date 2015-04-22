package utils.servlet.httpproxy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author yuanwei
 * @version ctreateTime:2012-12-27 下午3:15:12
 */
public class ProxyX509TrustManager implements X509TrustManager {
	final HttpProxyServlet	this$0;
	
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
	
	public ProxyX509TrustManager(HttpProxyServlet httpProxyServlet) {
		this$0=httpProxyServlet;
	}
	
	public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub
		
	}
	
	public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub
		
	}
	
}
