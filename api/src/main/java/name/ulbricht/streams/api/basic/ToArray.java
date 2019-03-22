package name.ulbricht.streams.api.basic;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns an array containing the elements of this stream.")
@Terminal
public final class ToArray<T> implements Function<Stream<T>, Object[]> {

	@Override
	public Object[] apply(final Stream<T> stream) {
		return stream.toArray();
	}

	@Override
	public String toString() {
		return ".toArray()";
	}
}
