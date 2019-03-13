package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Empty Stream", type = SOURCE)
public final class Empty implements Supplier<Stream<Object>> {

	@Override
	public Stream<Object> get() {
		return Stream.empty();
	}

	@Override
	public String toString() {
		return "Stream.empty()";
	}
}
