package utils.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页标签
 * @author stchou http://stchou.iteye.com/blog/1081883
 */
public class PageTag extends TagSupport {
	private static final long	serialVersionUID	=1L;
	final static Logger			log					=LoggerFactory.getLogger(PageTag.class);
	private int					rowCount			=50;										// 记录数
	private int					pageSize			=20;										// 页面大小
	private int					pageCount			=0;										// 多少页
	private int					pageNow				=1;										// 当前页
	/** 分页显示链接 */
	private String				url					=null;
	
	public int getRowCount() {
		return rowCount;
	}
	
	public void setRowCount(int rowCount) {
		this.rowCount=rowCount;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize=pageSize;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount=pageCount;
	}
	
	public int getPageNow() {
		return pageNow;
	}
	
	public void setPageNow(int pageNow) {
		this.pageNow=pageNow;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url=url;
	}
	
	public int doStartTag() throws JspException {
		JspWriter out=pageContext.getOut();// 得到输入对象
		
		if(log.isDebugEnabled()){
			log.debug(this.toString());
		}
		
		int isDiv=rowCount % pageSize;// 是否整除
		if(isDiv != 0){
			pageCount=rowCount / pageSize + 1;
		}else{
			pageCount=rowCount / pageSize;
		}
		
		System.out.println("pageSie=" + pageSize + ",pageCount=" + pageCount + ",pageNow=" + pageNow);
		System.out.println("请求的URL=" + url);
		
		StringBuilder html=new StringBuilder(2048);// 返回的结果
		
		html.append("<div id='pageTagDiv'>\n")
			.append("<script type=\"text/javascript\">\n")
				.append("function sendPageRequest(page) {\n")
				.append("var url=\"").append(url).append("\";\n")
				.append("var pageSize=\"").append(pageSize).append("\";\n")
				.append("var total=\"").append(rowCount).append("\";\n")
				.append("}")
			.append("</script>\n")
		.append("<a href=\"javascript:sendPageRequest('1')\">首页</a>")
		.append("<a href=\"javascript:sendPageRequest('" + (pageNow - 1) + "')\">上一页  </a>");
		for(int i=1;i <= pageCount;i++){
			if(i >= 1 && i <= pageCount) html.append("<a href=\"javascript:sendPageRequest('").append(i).append("')\">").append(i).append("</a>");
		}
		html.append("<a href=\"javascript:sendPageRequest('" +(pageNow + 1) +  "')\">下一页  </a>")
				.append("<a href=\"javascript:sendPageRequest('" +pageCount +  "')\">尾页  </a>")
				.append("共" + pageCount + "页");
		html.append("</div>");
		try{
			out.println(html);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return 0;
	}
}
