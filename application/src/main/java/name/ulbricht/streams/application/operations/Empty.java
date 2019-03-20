package name.ulbricht.streams.application.operations;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Creates a stream with no elements at all.")
@Source
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
