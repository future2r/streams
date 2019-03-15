package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "UpperCase", type = INTERMEDIATE, input = String.class, output = String.class, description = "Converts a string into its upper-case version.")
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
