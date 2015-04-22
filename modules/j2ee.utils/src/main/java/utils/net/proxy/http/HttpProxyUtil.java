package utils.net.proxy.http;

public class HttpProxyUtil{
	/** 根据url判断ContentType */
	public static String checkContentType(String url,String charset){
		//"Content-Type", "application/x-www-form-urlencoded"
		return null;
	}
	
	/** 根据url判断文件类型 */
	public static String checkUrlType(String url){
		//https://www.google.com/reader/view/?hl=zh-CN&tab=wy#stream/feed%2Fhttp%3A%2F%2Ffeed.feedsky.com%2Fmrxc
		if(url.indexOf(".")==-1) return "";
		return "text";
	}
	
	/** 处理网页中的链接 */
	public static String changeLink(String data){
		return null;
	}
}
