package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Creates a stream with all locales.")
@StreamOperation(type = SOURCE, output = Locale.class)
public final class Locales implements Supplier<Stream<Locale>> {

	@Override
	public Stream<Locale> get() {
		return Stream.of(Locale.getAvailableLocales());
	}

	@Override
	public String toString() {
		return "Stream.of(Locale.getAvailableLocales())";
	}
}
