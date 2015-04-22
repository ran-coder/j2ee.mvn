package j2ee.research.java.thread.threadlocal;
/**  
 * @author yuanwei  
 * @version ctreateTime:2012-5-21 下午3:18:07
 *   
 */
public class SimpleStatement implements Statement {
	private long id;
	public SimpleStatement(long id){
		this.id=id;
	}
	@Override
	public void executeQuery(String sql) {
		System.out.println("t-"+Thread.currentThread().getId() + "-SimpleStatement["+id+"]:executeQuery");
	}
	
	@Override
	public void close() {
		
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
		SimpleStatement other=(SimpleStatement)obj;
		if(id != other.id) return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("SimpleStatement [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
}
