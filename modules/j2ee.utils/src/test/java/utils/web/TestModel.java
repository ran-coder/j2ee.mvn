package utils.web;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2014-03-26 11:34
 * To change this template use File | Settings | File Templates.
 */
public class TestModel{
	private Long id;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id=id;
	}

	@Override
	public int hashCode(){ return new HashCodeBuilder().append(this.id).toHashCode();}

	@Override
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}
		if(getClass()!=obj.getClass()){
			return false;
		}
		final TestModel other=(TestModel) obj;
		return new EqualsBuilder().append(this.id, other.id).isEquals();
	}

	@Override
	public String toString(){
		return "TestModel{"+
				"id="+id+
				'}';
	}
}
