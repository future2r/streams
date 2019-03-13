package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "String Length Grouping", type = TERMINAL, input = String.class)
public final class StringLengthGrouping implements Function<Stream<String>, Object> {

	@Override
	public Object apply(final Stream<String> stream) {
		return stream.collect(Collectors.groupingBy(String::length));
	}

	@Override
	public String toString() {
		return ".collect(Collectors.groupingBy(String::length))";
	}
}
