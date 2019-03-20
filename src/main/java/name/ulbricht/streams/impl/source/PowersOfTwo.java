package name.ulbricht.streams.impl.source;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Generates a sequence of long values with the powers of two as a new stream.")
@Source
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
