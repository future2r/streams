package name.ulbricht.streams.api.basic;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream consisting of the elements of this stream, sorted according to natural order.")
@Intermediate
public final class Sorted<T extends Comparable<T>> implements Function<Stream<T>, Stream<T>> {

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.sorted();
	}

	@Override
	public String toString() {
		return ".sorted()";
	}
}