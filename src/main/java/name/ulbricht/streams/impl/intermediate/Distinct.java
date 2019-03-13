package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;

public final class Distinct implements IntermediateOperation<Object, Object> {

	@Override
	public String getSourceCode() {
		return ".distinct()";
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.distinct();
	}
}