package utils.apache.log;

import java.util.Date;
import java.util.Locale;

import utils.DateUtil;
import utils.StringUtil;

public class Log {
	private String ip;
	private Date date;
	private String method;
	private String url;
	private Integer stutas;
	private Integer ContentLength;
	
	public static Log format(String str){
		//192.168.39.31 - - [02/Jul/2010:14:45:56 +0800] "GET /crm/images/header1.png HTTP/1.1" 200 25406
		//192.168.39.31 - - [02/Jul/2010:14:45:55 +0800] "GET /crm/scripts/header.js HTTP/1.1" 200 2548
		//.* .* .*\[.*\] ".*" .* .*
		Log log=new Log();
		str=str.replaceAll(" \\+","+");
		String[] temp=str.split("\\[|\\]|\"");

		log.setIp(StringUtil.getArrayIndexValue(temp,0,"").trim().split(" ")[0]);
		log.setDate(DateUtil.string2Date(StringUtil.getArrayIndexValue(temp,1,null),"dd/MMM/yyyy:HH:mm:ssZ",Locale.ENGLISH));
		log.setMethod(StringUtil.getArrayIndexValue(temp,3,"").trim().split(" ")[0]);
		log.setUrl(StringUtil.getArrayIndexValue(temp,3,"").trim().split(" ")[1]);
		log.setStutas( StringUtil.toInt( StringUtil.getArrayIndexValue(temp,4,"").trim().split(" ")[0] ,0)  );
		log.setContentLength( StringUtil.toInt( StringUtil.getArrayIndexValue(temp,4,"").trim().split(" ")[1] ,0)  );
		
		if(log.getUrl()!=null &&log.getUrl().indexOf(".html")!=-1 && !log.getUrl().endsWith(".html")){
			log.setUrl(log.getUrl().split(".html")[0]+".html");
		}
		return log;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip=ip;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date=date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url=url;
	}
	public Integer getStutas() {
		return stutas;
	}
	public void setStutas(Integer stutas) {
		this.stutas=stutas;
	}
	public Integer getContentLength() {
		return ContentLength;
	}
	public void setContentLength(Integer contentLength) {
		ContentLength=contentLength;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method=method;
	}

	/** toJsonString */
	public String toJsonString(){
		StringBuilder retValue = new StringBuilder();
	
		retValue.append("{")
			.append("\"ip\" : \"").append(this.ip).append("\",")
			.append("\"date\" : \"").append(DateUtil.toString(this.date,DateUtil.DEFAULT)).append("\",")
			.append("\"method\" : \"").append(this.method).append("\",")
			.append("\"url\" : \"").append(this.url).append("\",")
			.append("\"stutas\" : \"").append(this.stutas).append("\",")
			.append("\"ContentLength\" : \"").append(this.ContentLength).append("\",")
			;
		if(retValue.length()>0)retValue.setLength(retValue.length()-1);
		return retValue.append("}").toString();
	}
	
	public static void main(String[] args) {
		System.out.println(Log.format("192.168.39.31 - - [02/Jul/2010:14:45:55 +0800] \"GET /crm/scripts/header.js HTTP/1.1\" 200 2548").toJsonString());
	}
}
