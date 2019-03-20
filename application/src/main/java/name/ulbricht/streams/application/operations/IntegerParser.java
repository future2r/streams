package name.ulbricht.streams.application.operations;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Parses a stream of strings into a stream of integer values.")
@Intermediate
public final class IntegerParser implements Function<Stream<String>, Stream<Integer>> {

	@Override
	public Stream<Integer> apply(final Stream<String> stream) {
		return stream.map(Integer::parseInt);
	}

	@Override
	public String toString() {
		return ".map(Integer::parseInt)";
	}
}
