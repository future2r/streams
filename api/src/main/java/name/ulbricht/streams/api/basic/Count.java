package name.ulbricht.streams.api.basic;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns the count of elements in this stream.")
@Terminal
public final class Count<T> implements Function<Stream<T>, Long> {

	@Override
	public Long apply(final Stream<T> stream) {
		return stream.count();
	}

	@Override
	public String toString() {
		return ".count()";
	}
}
