package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;

public final class Parallel implements IntermediateOperation<Object, Object> {

	@Override
	public String getSourceCode() {
		return ".parallel()";
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.parallel();
	}
}