package nio.mina.file.simple;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-16 下午3:45:00
 *   
 */
public class ClientConfig {
	private String host;
	private int port;
	private String input;
	private String output;
	private long start;
	private long end;
	private long blockSize;
	public ClientConfig(String input,String output){
		this.input=input;
		this.output=output;
	}
	public ClientConfig(String host,int port,String input,String output){
		this.host=host;
		this.port=port;
		this.input=input;
		this.output=output;
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host=host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port=port;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input=input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output=output;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start=start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end=end;
	}
	public long getBlockSize() {
		return blockSize;
	}
	public void setBlockSize(long blockSize) {
		this.blockSize=blockSize;
	}

}
