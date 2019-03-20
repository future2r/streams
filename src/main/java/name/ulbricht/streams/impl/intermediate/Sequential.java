package name.ulbricht.streams.impl.intermediate;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns an equivalent stream that is sequential."
		+ " May return itself, either because the stream was already sequential, or becaus ethe underlying stream state was modified to be sequential.")
@Intermediate
public final class Sequential<T> implements Function<Stream<T>, Stream<T>> {

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.sequential();
	}

	@Override
	public String toString() {
		return ".sequential()";
	}
}