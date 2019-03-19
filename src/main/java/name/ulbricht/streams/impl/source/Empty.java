package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Creates a stream with no elements at all.")
@StreamOperation(type = SOURCE)
public final class Empty<T> implements Supplier<Stream<T>> {

	@Override
	public Stream<T> get() {
		return Stream.empty();
	}

	@Override
	public String toString() {
		return "Stream.empty()";
	}
}
