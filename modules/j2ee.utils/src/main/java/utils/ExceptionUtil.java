package utils;

/**
 * @author yuanwei
 * @version ctreateTime:2011-8-29 上午10:49:45
 */
public class ExceptionUtil {
	public static class CheckedException extends Exception {
		private static final long	serialVersionUID	=2674732889909992734L;

		public CheckedException() {
			super();
		}
		public CheckedException(String message) {
			super(message);
		}
		public CheckedException(String message, Throwable cause) {
			super(message,cause);
		}
		public CheckedException(Throwable cause) {
			super(cause);
		}

	}

	public static class UnCheckedException extends RuntimeException {
		private static final long	serialVersionUID	=2674732889909992734L;

		public UnCheckedException() {
			super();
		}

		public UnCheckedException(String message) {
			super(message);
		}

		public UnCheckedException(String message, Throwable cause) {
			super(message,cause);
		}

		public UnCheckedException(Throwable cause) {
			super(cause);
		}
	}
	public static class ValidateException extends UnCheckedException {
		private static final long	serialVersionUID	=-5523396453221465479L;

		public ValidateException() {
			super();
		}

		public ValidateException(String message) {
			super(message);
		}

		public ValidateException(String message, Throwable cause) {
			super(message,cause);
		}

		public ValidateException(Throwable cause) {
			super(cause);
		}
	}

	public static void throwUnCheckedException(Exception e){
		RuntimeException uc=unCheckedException(e);
		throw uc;
	}
	/**
	 * 将CheckedException转换为UnCheckedException.
	 */
	public static RuntimeException unCheckedException(Exception e) {
		if(e instanceof RuntimeException){
			return (RuntimeException)e;
		}
		return new RuntimeException(e.getMessage(),e);
	}

	public static Exception checkedException(RuntimeException e) {
		return new Exception(e.getMessage(),e);
	}

	/** RuntimeException 不用try..catch */
	public static RuntimeException newRuntimeException(String message, Throwable e) {
		return new RuntimeException(message,e);
	}

	/** RuntimeException 不用try..catch */
	public static RuntimeException newRuntimeException(Throwable e) {
		return new RuntimeException(e);
	}

	/** RuntimeException 不用try..catch */
	public static RuntimeException newRuntimeException(String message) {
		return new RuntimeException(message);
	}

	/** RuntimeException 不用try..catch */
	public static RuntimeException newValidateException(String message) {
		return new RuntimeException(message);
	}

	/** RuntimeException 违法异常 */
	public static IllegalStateException newIllegalStateException(String message, Throwable e) {
		return new IllegalStateException(message,e);
	}

	/** RuntimeException 违法异常 */
	public static IllegalStateException newIllegalStateException(String message) {
		return new IllegalStateException(message);
	}
}
