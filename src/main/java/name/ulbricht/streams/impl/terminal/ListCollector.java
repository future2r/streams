package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "List Collector", type = TERMINAL)
public final class ListCollector implements Function<Stream<Object>, Object> {

	@Override
	public Object apply(final Stream<Object> stream) {
		return stream.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return ".collect(Collectors.toList())";
	}
}
