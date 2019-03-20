package name.ulbricht.streams.application.operations;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Prints the element to System.out and then forwards it.")
@Intermediate
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
