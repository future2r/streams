package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.beans.JavaBean;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Returns an Optional describing some element of the stream, or an empty Optional if the stream is empty.")
@StreamOperation(type = TERMINAL)
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
