package utils.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-26 上午10:20:31
 *   
 */
public class IteratorMapTag extends SimpleTagSupport{
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
		@SuppressWarnings("unchecked")
		Map<String,Object> map=(Map<String,Object>)getJspContext().getAttribute(var);
		if(map!=null){
			StringBuilder sb=new StringBuilder();
			for(Map.Entry<String, Object> entry:map.entrySet()){
				getJspContext().setAttribute(item,entry);
				//getJspBody().invoke(null);
				sb.append(entry.getKey()).append(",").append(entry.getValue()).append("<br/>");
			}
			getJspContext().getOut().write(sb.toString());
		}
		super.doTag();
	}
}
