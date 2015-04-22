package utils.taglib.select;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import utils.StringUtil;

/**
 * @author admin
 * <tag:select id="" name="selectName" style="" css="" items="${map}" default="${defaultValue}" onchange=''/> 
 */
public class SelectMapTag extends TagSupport {
	private static final long	serialVersionUID	=448837517808096336L;
	private String						id;
	private String						name;
	private String						style;
	private String						css;
	private Map<?,?>					items;
	private String						defaultValue;
	private String						onchange;


	public void release() {
		items=null;
		defaultValue=null;
		name=null;
		id=null;
		onchange=null;
	}

	public int doEndTag() throws JspException{
		JspWriter jspOut=pageContext.getOut();
		StringBuilder builder=new StringBuilder(1024);
		builder.append("<select id='").append(id).append('\'').append(' ')
			.append("name='").append(name).append('\'').append(' ')
			.append("style='").append(style).append('\'').append(' ')
			.append("class='").append(css).append('\'').append(' ')
			.append("onChange='").append(onchange).append("'>");
		builder.append("<option value='-1'>Please Select</option>");
		if(items != null){
			for(Map.Entry<?,?> entry:items.entrySet()){
				if(!StringUtil.isEmpty(defaultValue) && defaultValue.equals(entry.getValue())){
					builder.append("<option value='").append(entry.getValue()).append("' selected>").append(entry.getKey()).append("</option>");
				}else{
					builder.append("<option value='").append(entry.getValue()).append("'>").append(entry.getKey()).append("</option>");
				}
			}
		}
		builder.append("</select>");

		try{
			jspOut.append(builder);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			release();
		}
		return EVAL_PAGE;
	}

}
