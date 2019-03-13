package name.ulbricht.streams.impl;

public final class StringUtils {

	public static String quote(final String s) {
		return s.replace("\\", "\\\\");
	}

	private StringUtils() {
		// hidden
	}
}
