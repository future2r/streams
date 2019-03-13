package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Count Elements", type = TERMINAL)
public final class Count implements Function<Stream<Object>, Object> {

	@Override
	public Object apply(final Stream<Object> stream) {
		return stream.count();
	}

	@Override
	public String toString() {
		return ".count()";
	}
}
