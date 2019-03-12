package name.ulbricht.streams.impl.source;

import java.util.Locale;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.SourceOperation;

@Operation(output = Locale.class)
public final class Locales implements SourceOperation<Locale> {

	@Override
	public String getSourceCode() {
		return "Stream.of(Locale.getAvailableLocales())";
	}

	@Override
	public Stream<Locale> createStream() {
		return Stream.of(Locale.getAvailableLocales());
	}
}
