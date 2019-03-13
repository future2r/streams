package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Minimum", type = TERMINAL, input = Comparable.class)
public final class Min<T extends Comparable<T>> implements Function<Stream<T>, Object> {

	@Override
	public Object apply(final Stream<T> stream) {
		return stream.min(Comparator.naturalOrder());
	}

	@Override
	public String toString() {
		return ".min(Comparator.naturalOrder())";
	}
}
