package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Generates a sequence of long values with the powers of two as a new stream.")
@StreamOperation(type = SOURCE, output = Long.class)
public final class PowersOfTwo implements Supplier<Stream<Long>> {

	@Override
	public Stream<Long> get() {
		return Stream.iterate(1L, n -> n < (1L << 62), n -> n << 1);
	}

	@Override
	public String toString() {
		return "Stream.iterate(1L, n -> n < (1L << 62), n -> n << 1)";
	}
}
