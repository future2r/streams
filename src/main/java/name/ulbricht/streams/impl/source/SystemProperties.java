package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Java System Properties", type = SOURCE)
public final class SystemProperties implements Supplier<Stream<Object>> {

	@Override
	public Stream<Object> get() {
		return System.getProperties().keySet().stream();
	}

	@Override
	public String toString() {
		return "System.getProperties().keySet().stream()";
	}
}
