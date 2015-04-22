package utils;

public class TimeWatch{
	private long start=0;
	private long end=0;
	public TimeWatch start(){
		start=System.currentTimeMillis();
		return this;
	}
	public TimeWatch stop(){
		end=System.currentTimeMillis();
		return this;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}

}
