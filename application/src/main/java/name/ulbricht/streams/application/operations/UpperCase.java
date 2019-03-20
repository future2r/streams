package name.ulbricht.streams.application.operations;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Converts a string into its upper-case version.")
@Intermediate
public final class UpperCase implements Function<Stream<String>, Stream<String>> {

	@Override
	public Stream<String> apply(final Stream<String> stream) {
		return stream.map(String::toUpperCase);
	}

	@Override
	public String toString() {
		return ".map(String::toUpperCase)";
	}
}
