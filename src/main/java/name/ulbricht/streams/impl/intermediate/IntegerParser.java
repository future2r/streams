package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(name = "Integer Parser", input = String.class, output = Integer.class)
public final class IntegerParser implements IntermediateOperation<String, Integer> {

	@Override
	public String getSourceCode() {
		return ".map(Integer::parseInt)";
	}

	@Override
	public Stream<Integer> apply(final Stream<String> stream) {
		return stream.map(Integer::parseInt);
	}
}
