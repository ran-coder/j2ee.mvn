package j2ee.tutorial.apache.util.pool;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPool;

public class StringBufferFactory implements PoolableObjectFactory{
	public Object makeObject() {
		return new StringBuffer();
	}

	public void passivateObject(Object obj) {
		StringBuffer buf=(StringBuffer)obj;
		buf.setLength(0);
	}
	public void activateObject(Object obj) throws Exception {
		
	}

	public void destroyObject(Object obj) throws Exception {
		
	}

	public boolean validateObject(Object obj) {
		return false;
	}

	public static void main(String[] args) {
		new ReaderUtil(new StackObjectPool(new StringBufferFactory()));
	}

	static class ReaderUtil {
		private ObjectPool	pool;

		public ReaderUtil(ObjectPool pool) {
			this.pool=pool;
		}

		/**
		 * Dumps the contents of the {@link java.io.Reader} to a String, closing the
		 * {@link java.io.Reader} when done.
		 */
		public String readToString(Reader in) throws IOException {
			StringBuffer buf=null;
			try{
				buf=(StringBuffer)(pool.borrowObject());
				for(int c=in.read();c != -1;c=in.read()){
					buf.append((char)c);
				}
				return buf.toString();
			}catch(IOException e){
				throw e;
			}catch(Exception e){
				throw new RuntimeException("Unable to borrow buffer from pool" + e.toString());
			}finally{
				try{
					in.close();
				}catch(Exception e){
					// ignored
				}
				try{
					if(null != buf){
						pool.returnObject(buf);
					}
				}catch(Exception e){
					// ignored
				}
			}
		}
	}

	

}
