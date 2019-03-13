package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "ToString Mapper", type = INTERMEDIATE, output = String.class)
public final class ToStringMapper<T> implements Function<Stream<T>, Stream<String>> {

	@Override
	public Stream<String> apply(final Stream<T> stream) {
		return stream.map(Objects::toString);
	}

	@Override
	public String toString() {
		return ".map(Objects::toString)";
	}
}
