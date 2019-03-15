package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "System.out Peek", type = INTERMEDIATE, description = "Prints the element to System.out and then forwards it.")
public final class SystemOutPeek<T> implements Function<Stream<T>, Stream<T>> {

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.peek(System.out::println);
	}

	@Override
	public String toString() {
		return ".peek(System.out::println)";
	}
}
