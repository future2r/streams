package name.ulbricht.streams.impl.intermediate;

import java.util.Objects;
import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(name = "Non Null Filter")
public final class NonNullFilter implements IntermediateOperation<Object, Object> {

	@Override
	public String getSourceCode() {
		return ".filter(Objects::nonNull)";
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.filter(Objects::nonNull);
	}
}
