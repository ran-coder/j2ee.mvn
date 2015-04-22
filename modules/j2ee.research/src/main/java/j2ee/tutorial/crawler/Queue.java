package j2ee.tutorial.crawler;

/**
 * @author yuanwei
 * @version ctreateTime:2011-4-18 下午2:53:40
 */
import java.util.LinkedList;

/**
 * 数据结构队列
 */
public class Queue<T> {
	private LinkedList<T>	queue	=new LinkedList<T>();

	public void addLast(T t) {
		queue.addLast(t);
	}

	public T removeFirst() {
		return queue.removeFirst();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public boolean contians(T t) {
		return queue.contains(t);
	}

}
