package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Non Null Filter", type = INTERMEDIATE)
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
