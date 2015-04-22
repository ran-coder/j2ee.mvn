package utils;

import java.util.StringTokenizer;

public class IPUtil {
	public static long ipv4ToLong(String ipString) {
		long result=0;
		StringTokenizer token=new StringTokenizer(ipString,".");

		result+=Long.parseLong(token.nextToken()) << 24;
		result+=Long.parseLong(token.nextToken()) << 16;
		result+=Long.parseLong(token.nextToken()) << 8;
		result+=Long.parseLong(token.nextToken());
		return result;
	}

	public static String longToIpv4(long ipLong) {
		StringBuilder sb=new StringBuilder();
		sb.append(ipLong >>> 24);
		sb.append(".");
		sb.append(String.valueOf((ipLong & 0x00FFFFFF) >>> 16));
		sb.append(".");
		sb.append(String.valueOf((ipLong & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(ipLong & 0x000000FF));
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(ipv4ToLong("10.10.10.256"));
		System.out.println(longToIpv4(168430080));
	}
}
