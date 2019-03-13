package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Find Any", type = TERMINAL)
public final class FindAny implements Function<Stream<Object>, Object> {

	@Override
	public Optional<Object> apply(final Stream<Object> stream) {
		return stream.findAny();
	}

	@Override
	public String toString() {
		return ".findAny()";
	}
}
