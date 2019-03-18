package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.nio.charset.Charset;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Charsets List", type = SOURCE, output = Charset.class, description = "Creates a stream with all charsets.")
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
