/**
 * 
 */
package utils.net.proxy.http;

/**
 * @author yuanwei
 *
 */
public class HttpProxyHandlerImpl implements HttpProxyHandler{
	private String url;
	private String contentType;
	private String urlType;
	private String charset;
	private boolean isHttps;
	
	public HttpProxyHandlerImpl(){}
	
	public HttpProxyHandlerImpl(String url,String contentType,String charset){
		this.url=url;
		this.contentType=contentType;
		this.charset=charset;
		this.urlType=HttpProxyUtil.checkUrlType(url);
		if(url.startsWith("https"))isHttps=true;
	}

	public String getContentType(){
		if(contentType!=null && contentType.length()>0)
			return HttpProxyUtil.checkContentType(url,charset);
		return contentType;
	}

	public byte[] getHandledUrlData(String baseUrlPath){
		handleSessionAndCookie();
		return HttpProxyUtil.changeLink(new String(getUrlData())).getBytes();
	}

	public byte[] getUrlData(){
		return isHttps?getHttpsUrl():getHttpUrl();
	}

	public void handleSessionAndCookie() {

	}

	public byte[] getHttpUrl() {
		if("text/html".equals(urlType)){
			
		}else{
			
		}
		return null;
	}

	public byte[] getHttpsUrl() {
		return null;
	}

}
