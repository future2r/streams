package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Writes each element in the stream to System.out.")
@StreamOperation(type = TERMINAL)
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
