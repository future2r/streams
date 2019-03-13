package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Unordered", type = INTERMEDIATE)
public final class Unordered implements Function<Stream<Object>, Stream<Object>> {

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.unordered();
	}

	@Override
	public String toString() {
		return ".unordered()";
	}
}