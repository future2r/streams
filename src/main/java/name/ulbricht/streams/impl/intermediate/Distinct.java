package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Returns a stream consisting of the distinct elements (according to the natural order) of this stream.")
@StreamOperation(type = INTERMEDIATE)
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