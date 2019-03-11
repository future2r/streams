package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(input = String.class, output = String.class)
public final class UpperCase implements IntermediateOperation<String, String> {

	@Override
	public String getSourceCode() {
		return ".map(String::toUpperCase)";
	}

	@Override
	public Stream<String> processStream(final Stream<String> stream) {
		return stream.map(String::toUpperCase);
	}
}
