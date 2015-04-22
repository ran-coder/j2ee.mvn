package utils;

import javax.servlet.http.HttpServletRequest;


public class WebUtil {
	private HttpServletRequest request=null;

	/*
	 * 返回参数不进行编码转换
	 */
	public WebUtil(HttpServletRequest request){
		this.request=request;
	}
	public void getConfig() {
		//StringUtil.setValue(this.oldCharset,Constants.WEBUTIL_OLDCHARSET);
		//StringUtil.setValue(this.newCharset,Constants.WEBUTIL_NEWCHARSET);
	}

	public int getInt(String param,int defaultValue){
		return StringUtil.toInt(request.getParameter(param),defaultValue);
	}
	public int getInt(String param){
		return getInt(param,-1);
	}
	
	public long getLong(String param,long defaultValue){
		return StringUtil.toLong(request.getParameter(param),defaultValue);
	}
	public long getLong(String param){
		return getLong(param,-1);
	}
	

	public String getAttribute(String param,String defaultValue){
		Object obj=request.getAttribute(param);
		if(obj==null)return defaultValue;
		return StringUtil.toEmpty(obj.toString(),defaultValue);
	}
	public String getAttribute(String param){
		return getAttribute(param,"");
	}
	public int getAttribute(String param,int defaultValue){
		Object obj=request.getAttribute(param);
		if(obj==null)return defaultValue;
		return StringUtil.toInt(obj.toString(),defaultValue);
	}
}
