package utils.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 图片验证码标签
 * 
 * @author stchou
 */
public class CaptchaTag extends TagSupport {
	private static final long	serialVersionUID	=1L;
	private String					sessionName		="vcode";
	/** 验证码图片的宽度 */
	private int					width			=60;
	/** 验证码图片的高度 */
	private int					height			=20;
	/** 验证码字符个数 */
	private int					codeCount		=4;
	public CaptchaTag() {}

	public int doStartTag() throws JspException {
		JspWriter out=pageContext.getOut();// 得到输入对象
		System.out.println("SessionName=" + sessionName);
		System.out.println("codeCount=" + codeCount);
		System.out.println("width=" + width);
		System.out.println("height=" + height);
		try{

			String html="";// 返回的结果

			// AJAX异步请求
			html+=" <script type=\"text/javascript\">" +

			// "	  var aa=1;"+
					"function reloadImg(){" + "document.getElementById(\"vc\").src=\"CodeServlet?SessionName='" + sessionName + "'&codeCount=" + codeCount + "" + "&width=" + width
					+ "&height=" + height + "\";" + "}"
					+

					"var request;"
					+ // 定义了XMLHttpRequest对象
					// 创建XMLHttpRequest对象函数
					"function getRequestObject() {" + "if (window.ActiveXObject) {" + "request=new ActiveXObject(\"Microsoft.XMLHTTP\");" + "  } else if (window.XMLHttpRequest) {"
					+ "  request=new XMLHttpRequest();" + " } else {" + " window.alert(\"你的浏览器不支持XMLHTTPRequest，将无使用AJAX功能！\");" + " }" + " }" +

					// 回调方法实现:将服务器返回的消息更新到div中
					"function processResult() {" + " if ((request.readyState == 4) && " + "  (request.status == 200)) {" +
					// 显示到指定的组件中

					"  }" + "}" +

					// 发送get请求,绑定回调方法
					"function sendRequest(url) {" + " getRequestObject();" + // 创建XMLHTTPRequest对象
					" request.onreadystatechange=processResult;" + // 绑定回调方法，
					" request.open(\"GET\", url, true);" + // 发送get请求
					"request.send(null);" + "}" + "</script>";

			html+="<a href=\"javascript:reloadImg()\">" + "<img name=\"vc\" id=\"vc\"src=\"CodeServlet?SessionName='" + sessionName + "'&codeCount=" + codeCount + "&width="
					+ width + "&height=" + height + "\" alt=\"验证码\" />看不清</a>";

			out.println(html);// 显示结果
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	/* get and set */

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width=width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height=height;
	}

	public int getCodeCount() {
		return codeCount;
	}

	public void setCodeCount(int codeCount) {
		this.codeCount=codeCount;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName=sessionName;
	}

}
