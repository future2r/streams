package name.ulbricht.streams.impl.source;

import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamSource;

public final class Empty implements StreamSource<Object> {

	@Override
	public String getSourceCode() {
		return "Stream.empty()";
	}

	@Override
	public Stream<Object> createStream() {
		return Stream.empty();
	}
}
