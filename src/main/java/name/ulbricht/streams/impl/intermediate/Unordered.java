package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Returns an equivalent stream that is unordered."
		+ " May return itself, either because the stream was already unordered, or because the underlying stream state was modified to be unordered.")
@StreamOperation(type = INTERMEDIATE)
public final class Unordered<T> implements Function<Stream<T>, Stream<T>> {

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.unordered();
	}

	@Override
	public String toString() {
		return ".unordered()";
	}
}