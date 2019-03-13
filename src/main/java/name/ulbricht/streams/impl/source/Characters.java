package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;
import static name.ulbricht.streams.impl.StringUtils.quote;

import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Characters", type = SOURCE, output = Integer.class)
public final class Characters implements Supplier<Stream<Integer>> {

	private String text = "Hello World!";

	@Configuration(type = ConfigurationType.STRING, displayName = "Text")
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public Stream<Integer> get() {
		return this.text.chars().boxed();
	}

	@Override
	public String toString() {
		return String.format("\"%s\".chars().boxed()", quote(this.text));
	}
}
