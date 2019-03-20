package name.ulbricht.streams.api.basic;

import java.beans.JavaBean;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns the maximum element of this stream according to the natural order.")
@Terminal
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
