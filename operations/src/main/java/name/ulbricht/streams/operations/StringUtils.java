package name.ulbricht.streams.operations;

final class StringUtils {

	public static String quote(final String s) {
		return s.replace("\\", "\\\\");
	}

	private StringUtils() {
		// hidden
	}
}
