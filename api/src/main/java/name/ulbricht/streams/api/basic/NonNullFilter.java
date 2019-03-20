package name.ulbricht.streams.api.basic;

import java.beans.JavaBean;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream that only consists of the non-null elements of this stream.")
@Intermediate
public final class NonNullFilter<T> implements Function<Stream<T>, Stream<T>> {

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.filter(Objects::nonNull);
	}

	@Override
	public String toString() {
		return ".filter(Objects::nonNull)";
	}
}
