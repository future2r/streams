package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;

@Name("Double Parser")
@Input(String.class)
@Output(Double.class)
public final class DoubleParser implements IntermediateOperation<String, Double> {

	@Override
	public String getSourceCode() {
		return ".map(Double::parseInt)";
	}

	@Override
	public Stream<Double> processStream(final Stream<String> stream) {
		return stream.map(Double::parseDouble);
	}
}
