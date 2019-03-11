package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(input = String.class, output = String.class)
public final class LowerCase implements IntermediateOperation<String, String> {

	@Override
	public String getSourceCode() {
		return ".map(String::toLowerCase)";
	}

	@Override
	public Stream<String> processStream(final Stream<String> stream) {
		return stream.map(String::toLowerCase);
	}
}
