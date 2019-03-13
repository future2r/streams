package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.impl.StringUtils.quote;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.SourceOperation;

@Operation(name = "Characters", output = Integer.class)
@Configuration(name = "text", type = ConfigurationType.STRING, displayName = "Text")
public final class Characters implements SourceOperation<Integer> {

	private String text = "Hello World!";

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public String getSourceCode() {
		return String.format("\"%s\".chars().boxed()", quote(this.text));
	}

	@Override
	public Stream<Integer> createStream() {
		return this.text.chars().boxed();
	}
}
