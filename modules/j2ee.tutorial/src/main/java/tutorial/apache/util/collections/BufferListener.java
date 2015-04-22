package tutorial.apache.util.collections;

import org.apache.commons.collections.Buffer;

public class BufferListener implements Runnable {
	private Buffer	buffer;

	public BufferListener(Buffer buffer) {
		this.buffer=buffer;
	}

	public void run() {
		while(true){
			String message=(String)buffer.remove();
			System.out.println(message);
		}
	}
}
