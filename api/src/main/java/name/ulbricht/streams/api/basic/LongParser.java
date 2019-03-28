package name.ulbricht.streams.api.basic;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Parses a stream of strings into a stream of long values.")
@Intermediate
public final class LongParser implements Function<Stream<String>, Stream<Long>> {

	@Override
	public Stream<Long> apply(final Stream<String> stream) {
		return stream.map(Long::parseLong);
	}

	@Override
	public String toString() {
		return ".map(Long::parseInt)";
	}
}
