package name.ulbricht.streams.application.operations;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Writes each element in the stream to System.out.")
@Terminal
public final class SystemOut<T> implements Function<Stream<T>, Void> {

	@Override
	public Void apply(final Stream<T> stream) {
		stream.forEach(System.out::println);
		return null;
	}

	@Override
	public String toString() {
		return ".forEach(System.out::println)";
	}
}
