package utils.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-25 下午5:36:50
 *   
 */
public class AttributeTag extends SimpleTagSupport{
	private String name;
	private String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value=value;
	}
	@Override
	public void doTag() throws JspException, IOException {
		getJspContext().getOut().write(new StringBuilder("name=").append(name==null?"":name).append(",value=").append(value==null?"":value).toString());
	}
}
