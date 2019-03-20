package name.ulbricht.streams.application.operations;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns an array containing the elements of this stream.")
@Terminal
public final class ToArray<T> implements Function<Stream<T>, T[]> {

	@SuppressWarnings("unchecked")
	@Override
	public T[] apply(final Stream<T> stream) {
		return (T[]) stream.toArray();
	}

	@Override
	public String toString() {
		return ".toArray()";
	}
}
