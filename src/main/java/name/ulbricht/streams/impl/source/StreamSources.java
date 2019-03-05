package name.ulbricht.streams.impl.source;

import name.ulbricht.streams.api.StreamSource;

public final class StreamSources {

	@SuppressWarnings("unchecked")
	public static final Class<? extends StreamSource<?>>[] IMPLEMENTATIONS = new Class[] { RandomIntegerGenerator.class,
			RandomDoubleGenerator.class, TextLines.class, TextFileReader.class };

	private StreamSources() {
		// hidden
	}
}
