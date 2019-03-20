package name.ulbricht.streams.impl.terminal;

import java.beans.JavaBean;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns an Optional describing some element of the stream, or an empty Optional if the stream is empty.")
@Terminal
public final class FindAny<T> implements Function<Stream<T>, Optional<T>> {

	@Override
	public Optional<T> apply(final Stream<T> stream) {
		return stream.findAny();
	}

	@Override
	public String toString() {
		return ".findAny()";
	}
}
