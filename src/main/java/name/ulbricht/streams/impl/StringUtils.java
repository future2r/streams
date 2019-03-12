package name.ulbricht.streams.impl;

public final class StringUtils {

	public static String quote(final String s) {
		return s.replace("\\", "\\\\");
	}

	public static String omit(final String s, final int max) {
		return s.substring(0, Math.min(max, s.length())) + (s.length() > max ? "…" : "");
	}

	private StringUtils() {
		// hidden
	}
}
