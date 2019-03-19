package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "eturns an equivalent stream that is parallel."
		+ " May return itself, either because the stream was already parallel, or because the underlying stream state was modified to be parallel.")
@StreamOperation(type = INTERMEDIATE)
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