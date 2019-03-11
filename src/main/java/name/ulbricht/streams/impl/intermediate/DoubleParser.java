package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(name = "Double Parser", input = String.class, output = Double.class)
public final class DoubleParser implements IntermediateOperation<String, Double> {

	@Override
	public String getSourceCode() {
		return ".map(Double::parseDouble)";
	}

	@Override
	public Stream<Double> processStream(final Stream<String> stream) {
		return stream.map(Double::parseDouble);
	}
}
