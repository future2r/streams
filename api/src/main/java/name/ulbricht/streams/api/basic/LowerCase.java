package name.ulbricht.streams.api.basic;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Converts a string in its lower-case version.")
@Intermediate
public final class LowerCase implements Function<Stream<String>, Stream<String>> {

	@Override
	public Stream<String> apply(final Stream<String> stream) {
		return stream.map(String::toLowerCase);
	}

	@Override
	public String toString() {
		return ".map(String::toLowerCase)";
	}
}
