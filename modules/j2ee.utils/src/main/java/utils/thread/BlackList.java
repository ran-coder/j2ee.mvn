package utils.thread;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArraySet;

public class BlackList<T> implements Serializable{
	private static final long	serialVersionUID	=-4674719775994622843L;
	private String name;
	private transient  CopyOnWriteArraySet<T> list=new CopyOnWriteArraySet<T>();

	public BlackList(String name){
		this.name=name;
	}
	public BlackList<T> add(T item){
		list.add(item);
		return this;
	}
	public BlackList<T> remove(T item){
		list.remove(item);
		return this;
	}

	public boolean contains(T item){
		return list.contains(item);
	}
	public BlackList<T> clear(){
		list.clear();
		return this;
	}
	@Override
	public int hashCode() {
		final int prime=31;
		int result=1;
		result=prime * result + ((list == null) ? 0 : list.hashCode());
		result=prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		BlackList<T> other=(BlackList<T>)obj;
		if(list == null){
			if(other.list != null) return false;
		}else if(!list.equals(other.list)) return false;
		if(name == null){
			if(other.name != null) return false;
		}else if(!name.equals(other.name)) return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("BlackList [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
}
