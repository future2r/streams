package name.ulbricht.streams.impl.terminal;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;

@Operation(name = "String Length Grouping", input = String.class)
public final class StringLengthGrouping implements TerminalOperation<String> {

	@Override
	public String getSourceCode() {
		return ".collect(Collectors.groupingBy(String::length))";
	}

	@Override
	public Object terminateStream(final Stream<String> stream) {
		return stream.collect(Collectors.groupingBy(String::length));
	}
}
