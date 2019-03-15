package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "ToArray", type = TERMINAL, description = "Returns an array containing the elements of this stream.")
public final class ToArray<T> implements Function<Stream<T>, T[]> {

	@SuppressWarnings("unchecked")
	@Override
	public T[] apply(final Stream<T> stream) {
		return (T[]) stream.toArray();
	}

	@Override
	public String toString() {
		return ".toArray()";
	}
}
