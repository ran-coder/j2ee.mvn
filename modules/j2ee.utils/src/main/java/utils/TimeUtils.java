package utils;

import java.util.Stack;

public class TimeUtils {
	private static Stack<Long> times = new Stack<Long>();

    /**
     * 开始统计时间.
     */
    public static void begin() {
            times.add(System.currentTimeMillis());
    }

    /**
     * 结束统计时间 并输出结果.
     */
    public static void end(String title) {
            System.out.println(title + " : " + (System.currentTimeMillis() - times.pop()));
    }

    /**
     * sleep等待,单位毫秒.
     */
    public static void sleep(long millis) {
            try {
                    Thread.sleep(millis);
            } catch (InterruptedException e) {//NOSONAR
            }
    }

    public static void main(String[] args) {
    	TimeUtils.begin();
    	TimeUtils.sleep(1000);
    	TimeUtils.end("111");
    	System.out.println(Long.parseLong("XchkwPMsZ9iUKy9jyKambzyz",36));
	}
}
