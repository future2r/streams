package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "System.out", type = TERMINAL)
public final class SystemOut implements Function<Stream<Object>, Object> {

	@Override
	public Object apply(final Stream<Object> stream) {
		stream.forEach(System.out::println);
		return null;
	}

	@Override
	public String toString() {
		return ".forEach(System.out::println)";
	}
}
