package name.ulbricht.streams.api.basic;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "eturns an equivalent stream that is parallel."
		+ " May return itself, either because the stream was already parallel, or because the underlying stream state was modified to be parallel.")
@Intermediate
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