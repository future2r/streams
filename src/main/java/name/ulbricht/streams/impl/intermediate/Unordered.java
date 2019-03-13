package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(name = "Unordered")
public final class Unordered implements IntermediateOperation<Object, Object> {

	@Override
	public String getSourceCode() {
		return ".unordered()";
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.unordered();
	}
}