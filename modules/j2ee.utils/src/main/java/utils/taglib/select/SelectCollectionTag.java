package utils.taglib.select;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author admin
 * <iit:select name= "selectName" items="${selectList}" itemValue = "selectValue" itemLabel = "selectLabel" selectValue="${selectValue}"/>  
 */
public class SelectCollectionTag extends TagSupport {
	private static final long	serialVersionUID	=448837517808096336L;

	/**
	 * The {@link Collection}, {@link Map} or array of objects used to generate the inner '<code>option</code>' tags.
	 */
	private Object						items;
	
	/**
	 * The name of the property mapped to the '<code>value</code>' attribute of the '<code>option</code>' tag.
	 */
	private String						itemValue;
	
	/**
	 * The name of the property mapped to the inner text of the '<code>option</code>' tag.
	 */
	private String						itemLabel;
	private String						selectValue;
	private String						emptyValue;
	private String						name;
	private String						id;
	private String						onchange;
	
	public void release() {
		items=null;
		itemValue=null;
		itemLabel=null;
		selectValue=null;
		emptyValue=null;
		name=null;
		id=null;
		onchange=null;
	}
	
	public int doEndTag() throws JspException {
		JspWriter jspOut=pageContext.getOut();
		try{
			jspOut.append("<select id=\"" + getId() + "\" name=\"" + name + "\" " + getOnchange() + " >");
			jspOut.append("<option value=\"" + getEmptyValue() + "\">Please Select</option>");
			if(items != null){
				Collection<?> itemsList=(Collection<?>)items;
				for(Iterator<?> iterator=itemsList.iterator();iterator.hasNext();){
					Object obj=(Object)iterator.next();
					Object value=BeanUtils.getProperty(obj,itemValue);
					Object label=BeanUtils.getProperty(obj,itemLabel);
					if(value != null && value.equals(selectValue)){
						jspOut.append("<option value=\"" + value + "\" selected = \"selected\">" + label + "</option>");
					}else{
						jspOut.append("<option value=\"" + value + "\">" + label + "</option>");
					}
					
				}
			}
			jspOut.append("</select>");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			itemValue=null;
			itemLabel=null;
			selectValue=null;
			emptyValue=null;
			name=null;
			id=null;
			onchange=null;
		}
		
		return EVAL_PAGE;
	}
	
	public String getId() {
		if(id == null){
			id=name;
		}
		return id;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setId(String id) {
		this.id=id;
	}
	
	public void setItems(Object items) {
		this.items=items;
	}
	
	public void setItemValue(String itemValue) {
		this.itemValue=itemValue;
	}
	
	public void setItemLabel(String itemLabel) {
		this.itemLabel=itemLabel;
	}
	
	public void setSelectValue(String selectValue) {
		this.selectValue=selectValue;
	}
	
	public void setEmptyValue(String emptyValue) {
		this.emptyValue=emptyValue;
	}
	
	public String getEmptyValue() {
		if(emptyValue == null){
			emptyValue="-1";
		}
		return emptyValue;
	}
	
	public void setOnchange(String onchange) {
		this.onchange=onchange;
	}
	
	public String getOnchange() {
		if(onchange == null){ return ""; }
		return "onchange=\"" + onchange + "\"";
		
	}
}
