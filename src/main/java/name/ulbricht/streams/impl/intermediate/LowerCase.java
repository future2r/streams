package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Lower Case", type = INTERMEDIATE, input = String.class, output = String.class, description = "Converts a string in its lower-case version.")
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
