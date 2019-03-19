package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.nio.charset.Charset;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Creates a stream with all charsets.")
@StreamOperation(type = SOURCE, output = Charset.class)
public final class Encodings implements Supplier<Stream<Charset>> {

	@Override
	public Stream<Charset> get() {
		return Charset.availableCharsets().values().stream();
	}

	@Override
	public String toString() {
		return "Charset.availableCharsets().values().stream()";
	}
}
