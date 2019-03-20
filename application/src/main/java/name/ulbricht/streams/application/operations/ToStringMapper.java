package name.ulbricht.streams.application.operations;

import java.beans.JavaBean;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream with the string representation of the elements of this stream.")
@Intermediate
public final class ToStringMapper<T> implements Function<Stream<T>, Stream<String>> {

	@Override
	public Stream<String> apply(final Stream<T> stream) {
		return stream.map(Objects::toString);
	}

	@Override
	public String toString() {
		return ".map(Objects::toString)";
	}
}
