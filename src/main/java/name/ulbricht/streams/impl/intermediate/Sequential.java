package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Sequential", type = INTERMEDIATE)
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