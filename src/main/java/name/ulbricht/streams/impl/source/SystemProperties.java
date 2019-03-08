package name.ulbricht.streams.impl.source;

import java.util.Objects;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Name("Java System Properties")
@Output(String.class)
public final class SystemProperties implements StreamSource<String> {

	@Override
	public String getSourceCode() {
		return "System.getProperties().keySet().stream().map(Objects::toString)";
	}

	@Override
	public Stream<String> createStream() {
		return System.getProperties().keySet().stream().map(Objects::toString);
	}
}
