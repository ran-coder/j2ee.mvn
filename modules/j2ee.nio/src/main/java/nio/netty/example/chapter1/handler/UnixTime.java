package nio.netty.example.chapter1.handler;

import java.util.Date;

import utils.DateUtil;

public class UnixTime {
	private final int	value;

	public UnixTime(int value) {
		this.value=value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return DateUtil.toString(new Date(value * 1000L));
	}
}