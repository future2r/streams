package name.ulbricht.streams.files;

import java.beans.JavaBean;
import java.nio.charset.Charset;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Creates a stream with all charsets.")
@Source
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
