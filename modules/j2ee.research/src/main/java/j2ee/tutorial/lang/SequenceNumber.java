package j2ee.tutorial.lang;

public class SequenceNumber {
	// ①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
	private static ThreadLocal<Integer>	threadLocalSequenceNumber	=new ThreadLocal<Integer>() {
		public Integer initialValue() {
			return 0;
		}
	};

	// ②获取下一个序列值
	public int getNextNum() {
		threadLocalSequenceNumber.set(threadLocalSequenceNumber.get() + 1);
		return threadLocalSequenceNumber.get();
	}

	public static void main(String[] args) {
		SequenceNumber sn=new SequenceNumber();
		// ③ 3个线程共享sn，各自产生序列号
		new TestClient(sn).start();
		new TestClient(sn).start();
		new TestClient(sn).start();
	}

	private static class TestClient extends Thread {
		private SequenceNumber	sequenceNumber;

		public TestClient(SequenceNumber sn) {
			this.sequenceNumber=sn;
		}

		public void run() {
			for(int i=0;i < 3;i++){// ④每个线程打出3个序列值
				System.out.println("thread[" + Thread.currentThread().getName() + "] sn[" + sequenceNumber.getNextNum() + "]");
			}
		}
	}
}