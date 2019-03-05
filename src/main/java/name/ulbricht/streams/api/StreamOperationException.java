package name.ulbricht.streams.api;

public class StreamOperationException extends Exception {

	private static final long serialVersionUID = 1L;

	public StreamOperationException() {
		// super();
	}

	public StreamOperationException(final String message) {
		super(message);
	}

	public StreamOperationException(final Throwable cause) {
		super(cause);
	}

	public StreamOperationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public StreamOperationException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
