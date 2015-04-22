package utils.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-26 上午10:20:31
 *   
 */
public class DynamicAttributesTag extends SimpleTagSupport implements DynamicAttributes{
	private List<String> keys;
	private List<Object> values;

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys=keys;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values=values;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if(keys!=null&& values!=null ){//&& keys.size()==values.size()
			StringBuilder sb=new StringBuilder();
			for(int i=0,j=keys.size();i<j;i++){
				sb.append(keys.get(i)).append("=").append(values.get(i)).append("<br/>");
			}
			getJspContext().getOut().write(sb.toString());
		}
		super.doTag();
	}

	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		System.out.println("uri="+uri+",localName="+localName+",value="+value);
		if(keys==null)keys=new ArrayList<String>();
		if(values==null)values=new ArrayList<Object>();
		keys.add(localName);
		values.add(value);
	}
}
