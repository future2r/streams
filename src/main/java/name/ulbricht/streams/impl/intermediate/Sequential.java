package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;

public final class Sequential implements IntermediateOperation<Object, Object> {

	@Override
	public String getSourceCode() {
		return ".sequential()";
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.sequential();
	}
}