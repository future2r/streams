package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Integer Parser", type = INTERMEDIATE, input = String.class, output = Integer.class)
public final class IntegerParser implements Function<Stream<String>, Stream<Integer>> {

	@Override
	public Stream<Integer> apply(final Stream<String> stream) {
		return stream.map(Integer::parseInt);
	}

	@Override
	public String toString() {
		return ".map(Integer::parseInt)";
	}
}
