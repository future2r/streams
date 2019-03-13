package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Find First", type = TERMINAL)
public final class FindFirst implements Function<Stream<Object>, Object> {

	@Override
	public Optional<Object> apply(final Stream<Object> stream) {
		return stream.findFirst();
	}

	@Override
	public String toString() {
		return ".findFirst()";
	}
}
