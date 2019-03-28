package name.ulbricht.streams.extended;

import java.beans.JavaBean;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Creates a stream with all locales.")
@Source
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
