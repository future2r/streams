package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Sorted", type = INTERMEDIATE, input = Comparable.class, output = Comparable.class, description = "Returns a stream consisting of the elements of this stream, sorted according to natural order.")
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