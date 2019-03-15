package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Maximum", type = TERMINAL, input = Comparable.class, description = "Returns the maximum element of this stream according to the natural order.")
public final class Max<T extends Comparable<T>> implements Function<Stream<T>, Optional<T>> {

	@Override
	public Optional<T> apply(final Stream<T> stream) {
		return stream.max(Comparator.naturalOrder());
	}

	@Override
	public String toString() {
		return ".max(Comparator.naturalOrder())";
	}
}
