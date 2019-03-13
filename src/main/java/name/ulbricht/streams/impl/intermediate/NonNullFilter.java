package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Non Null Filter", type = INTERMEDIATE)
public final class NonNullFilter implements Function<Stream<Object>, Stream<Object>> {

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.filter(Objects::nonNull);
	}

	@Override
	public String toString() {
		return ".filter(Objects::nonNull)";
	}
}
