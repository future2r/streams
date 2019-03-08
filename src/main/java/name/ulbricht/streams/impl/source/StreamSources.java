package name.ulbricht.streams.impl.source;

import name.ulbricht.streams.api.StreamSource;

public final class StreamSources {

	@SuppressWarnings("unchecked")
	public static final Class<? extends StreamSource<?>>[] IMPLEMENTATIONS = new Class[] { Empty.class,
			RandomIntegerGenerator.class, RandomDoubleGenerator.class, TextLines.class, TextFileReader.class,
			FindFiles.class };

	private StreamSources() {
		// hidden
	}
}
