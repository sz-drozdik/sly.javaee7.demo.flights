package sly.javaee7.commons.exc;

public class AppException extends RuntimeException {

	public AppException(Throwable th) {
		this(null, th);
	}

	public AppException(String msg, Throwable th) {
		super(msg, th);
	}

	private static final long serialVersionUID = 1L;

}
