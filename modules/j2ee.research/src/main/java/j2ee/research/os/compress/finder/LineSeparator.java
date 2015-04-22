package j2ee.research.os.compress.finder;

public final class LineSeparator {
	public static final byte Unix = '\n';
	public static final byte[] Windows = "\r\n".getBytes();
	public static final byte Macintosh = '\r';
	public static final byte Web = '\n';

}
