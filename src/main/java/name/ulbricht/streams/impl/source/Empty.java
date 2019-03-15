package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Empty Stream", type = SOURCE, description = "Creates a stream with no elements at all.")
public final class Empty<T> implements Supplier<Stream<T>> {

	@Override
	public Stream<T> get() {
		return Stream.empty();
	}

	@Override
	public String toString() {
		return "Stream.empty()";
	}
}
