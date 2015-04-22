package utils.taglib;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-26 上午10:20:31
 *   
 */
public class IteratorTag extends SimpleTagSupport{
	private String var;
	private String item;
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var=var;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item=item;
	}

	@Override
	public void doTag() throws JspException, IOException {
		Collection<?> list=(Collection<?>)getJspContext().getAttribute(var);
		if(list!=null){
			for(Object o:list){
				getJspContext().setAttribute(item,o);
				getJspBody().invoke(null);
			}
		}
		super.doTag();
	}
}
