package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.beans.JavaBean;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Collects all elements of the stream into a List.")
@StreamOperation(type = TERMINAL)
public final class ListCollector<T> implements Function<Stream<T>, List<T>> {

	@Override
	public List<T> apply(final Stream<T> stream) {
		return stream.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return ".collect(Collectors.toList())";
	}
}
