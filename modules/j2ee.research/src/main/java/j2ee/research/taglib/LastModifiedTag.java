package j2ee.research.taglib;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2014-03-28 15:55
 * To change this template use File | Settings | File Templates.
 */
public class LastModifiedTag extends TagSupport {
	public int doEndTag() {
		try {
			HttpServletRequest request =(HttpServletRequest)pageContext.getRequest();
			String path = pageContext.getServletContext().getRealPath(	request.getServletPath());
			File file = new File(path);
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG);
			pageContext.getOut().println(formatter.format(new Date(file.lastModified())));
		} catch (IOException ignored) { }
		return EVAL_PAGE;
	}
}
