package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "ToArray", type = TERMINAL)
public final class ToArray implements Function<Stream<Object>, Object> {

	@Override
	public Object apply(final Stream<Object> stream) {
		return stream.toArray();
	}

	@Override
	public String toString() {
		return ".toArray()";
	}
}
