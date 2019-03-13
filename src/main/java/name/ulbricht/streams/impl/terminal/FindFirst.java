package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Find First", type = TERMINAL)
public final class FindFirst<T> implements Function<Stream<T>, Optional<T>> {

	@Override
	public Optional<T> apply(final Stream<T> stream) {
		return stream.findFirst();
	}

	@Override
	public String toString() {
		return ".findFirst()";
	}
}
