package utils.taglib;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
//http://royzhou1985.javaeye.com/blog/412697
public class HelloWorldTag extends SimpleTagSupport{
	public void doTag() throws JspException, IOException {
		getJspContext().getOut().write("HelloWorld " + new Date());
	}
}