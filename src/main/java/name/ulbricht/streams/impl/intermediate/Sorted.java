package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Sorted", type = INTERMEDIATE, input = Comparable.class, output = Comparable.class)
public final class Sorted
		implements Function<Stream<Comparable<? super Comparable<?>>>, Stream<Comparable<? super Comparable<?>>>> {

	@Override
	public Stream<Comparable<? super Comparable<?>>> apply(final Stream<Comparable<? super Comparable<?>>> stream) {
		return stream.sorted();
	}

	@Override
	public String toString() {
		return ".sorted()";
	}
}