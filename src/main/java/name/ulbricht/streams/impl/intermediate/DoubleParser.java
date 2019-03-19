package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Parses a stream of strings into a stream of double values.")
@StreamOperation(type = INTERMEDIATE, input = String.class, output = Double.class)
public final class DoubleParser implements Function<Stream<String>, Stream<Double>> {

	@Override
	public Stream<Double> apply(final Stream<String> stream) {
		return stream.map(Double::parseDouble);
	}

	@Override
	public String toString() {
		return ".map(Double::parseDouble)";
	}
}
