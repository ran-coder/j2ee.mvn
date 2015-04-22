package j2ee.research.java.thread.threadlocal;


/**  
 * @author yuanwei  
 * @version ctreateTime:2012-5-21 下午2:39:01
 *   
 */
public class SimpleConnection implements Connection {
	private long id;
	public SimpleConnection(long id){
		this.id=id;
	}
	@Override
	public Statement createStatement(){
		//System.out.println("SimpleConnection["+id+"] connect");
		return new SimpleStatement(id);
	}
	
	@Override
	public void close() {
		System.out.println("SimpleConnection["+id+"] close");
	}
	@Override
	public int hashCode() {
		final int prime=31;
		int result=1;
		result=prime * result + (int)(id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		SimpleConnection other=(SimpleConnection)obj;
		if(id != other.id) return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("SimpleConnection [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
}
