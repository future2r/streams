package name.ulbricht.streams.impl.intermediate;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream consisting of the distinct elements (according to the natural order) of this stream.")
@Intermediate
public final class Distinct<T> implements Function<Stream<T>, Stream<T>> {

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.distinct();
	}

	@Override
	public String toString() {
		return ".distinct()";
	}
}