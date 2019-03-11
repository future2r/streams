package name.ulbricht.streams.impl.source;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Name("Java System Properties")
@Output(Object.class)
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
