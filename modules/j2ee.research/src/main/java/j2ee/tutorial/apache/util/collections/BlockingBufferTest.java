package j2ee.tutorial.apache.util.collections;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.BlockingBuffer;
import org.apache.commons.collections.buffer.BoundedFifoBuffer;

public class BlockingBufferTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// Create a Blocking Buffer
		Buffer buffer=BlockingBuffer.decorate(new BoundedFifoBuffer());
		// Create Thread to continously remove( ) from the previous Buffer
		BufferListener listener=new BufferListener(buffer);
		Thread listenerThread=new Thread(listener);
		listenerThread.start();
		buffer.add("Hello World!");
		buffer.add("Goodbye, Y'all.");

	}
}
