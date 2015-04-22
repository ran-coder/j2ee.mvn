package tutorial.crawler;

/**
 * @author yuanwei
 * @version ctreateTime:2011-4-18 下午2:57:07
 */
public interface LinkFilter {
	public boolean accept(String url);
}
