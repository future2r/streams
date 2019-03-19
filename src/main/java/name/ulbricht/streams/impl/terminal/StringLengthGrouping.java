package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.beans.JavaBean;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Groups all strings in the stream into a map using their length as the key.")
@StreamOperation(type = TERMINAL, input = String.class)
public final class StringLengthGrouping implements Function<Stream<String>, Map<Integer, List<String>>> {

	@Override
	public Map<Integer, List<String>> apply(final Stream<String> stream) {
		return stream.collect(Collectors.groupingBy(String::length));
	}

	@Override
	public String toString() {
		return ".collect(Collectors.groupingBy(String::length))";
	}
}
