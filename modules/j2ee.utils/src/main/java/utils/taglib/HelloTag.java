package utils.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
//http://royzhou1985.javaeye.com/blog/412697
public class HelloTag extends SimpleTagSupport{

	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void doTag() throws JspException, IOException {
		getJspContext().getOut().write("Welcome " + userName + "!");
	}
}