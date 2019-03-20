package name.ulbricht.streams.impl.terminal;

import java.beans.JavaBean;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns an Optional describing the first element of this stream, or an empty Optional if the stream is empty."
		+ " If the stream has no encounter order, then any element may be returned.")
@Terminal
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
