package utils.net.proxy.http;

public interface HttpProxyHandler {

	/** 判断url文件属性 */
	public String getContentType();
	
	/** 抓url数据 */
	public byte[] getUrlData();
	
	/** 处理网页中的链接 */
	public byte[] getHandledUrlData(String baseUrlPath);
	
	/** 处理url的session cookie */
	public void handleSessionAndCookie();
	
	/** 抓非https url数据 */
	public byte[] getHttpUrl();
	
	/** 抓https url数据 */
	public byte[] getHttpsUrl();
}
