package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.beans.JavaBean;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Returns the maximum element of this stream according to the natural order.")
@StreamOperation(type = TERMINAL, input = Comparable.class)
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
