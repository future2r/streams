package name.ulbricht.streams.impl.source;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.StreamSource;

@Operation(name = "Java System Properties")
public final class SystemProperties implements StreamSource<Object> {

	@Override
	public String getSourceCode() {
		return "System.getProperties().keySet().stream()";
	}

	@Override
	public Stream<Object> createStream() {
		return System.getProperties().keySet().stream();
	}
}
