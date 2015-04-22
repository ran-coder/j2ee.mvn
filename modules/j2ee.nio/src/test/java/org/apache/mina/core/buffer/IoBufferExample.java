package org.apache.mina.core.buffer;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import utils.test.Tester;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-17 下午4:56:11
 *   
 */
public class IoBufferExample {

	public static void testTime() {
		final long runtimes=10000;
		final String charset="UTF-8";
		final CharsetEncoder encoder=Charset.forName(charset).newEncoder();
		final String small="11-11-17 格林尼治标准时间+0800下午4时59分09秒: [INFO] Using 'UTF-8' encoding to copy filtered resources.";
		final String big="11-11-17 格林尼治标准时间+0800下午4时59分09秒: [INFO] Using 'UTF-8' encoding to copy filtered resources.11-11-17 格林尼治标准时间+0800下午4时59分09秒: [INFO] Using 'UTF-8' encoding to copy filtered resources.11-11-17 格林尼治标准时间+0800下午4时59分09秒: [INFO] Using 'UTF-8' encoding to copy filtered resources.";
		Tester[]	tests			={
			new Tester("putString") {
				public void test() throws Exception {
					IoBuffer buff=IoBuffer.allocate(1024).setAutoExpand(true);
					for(int i=0;i<runtimes;i++){
						buff.clear();
						buff.putString(small,encoder);
					}
				}
			},
			new Tester("putString big") {
				public void test() throws Exception {
					IoBuffer buff=IoBuffer.allocate(1024).setAutoExpand(true);
					for(int i=0;i<runtimes;i++){
						buff.clear();
						buff.putString(big,encoder);
					}
				}
			},
			new Tester("putObject") {
				public void test() throws Exception {
					IoBuffer buff=IoBuffer.allocate(1024).setAutoExpand(true);
					for(int i=0;i<runtimes;i++){
						buff.clear();
						buff.putObject(small);
					}
				}
			},
			new Tester("putObject big") {
				public void test() throws Exception {
					IoBuffer buff=IoBuffer.allocate(1024).setAutoExpand(true);
					for(int i=0;i<runtimes;i++){
						buff.clear();
						buff.putObject(big);
					}
				}
			},
			new Tester("putBytes") {
				public void test() throws Exception {
					IoBuffer buff=IoBuffer.allocate(1024).setAutoExpand(true);
					for(int i=0;i<runtimes;i++){
						buff.clear();
						buff.put(small.getBytes(charset));
					}
				}
			},
			new Tester("putBytes big") {
				public void test() throws Exception {
					IoBuffer buff=IoBuffer.allocate(1024).setAutoExpand(true);
					for(int i=0;i<runtimes;i++){
						buff.clear();
						buff.put(big.getBytes(charset));
					}
				}
			}
		};
		for(Tester test : tests)
			test.run(Tester.SEC);
	}

	public static void main(String[] args) {
		 testTime();
	}
}
