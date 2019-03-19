package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Parallel", type = INTERMEDIATE, description = "Returns an equivalent stream that is parallel. "
		+ "May return itself, either because the stream was already parallel, or because the underlying stream state was modified to be parallel.")
public final class Parallel<T> implements Function<Stream<T>, Stream<T>> {

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.parallel();
	}

	@Override
	public String toString() {
		return ".parallel()";
	}
}