package name.ulbricht.streams.impl.source;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.api.StreamSource;

@Operation(name = "Characters", output = Integer.class)
@Configuration(name = "text", type = ConfigurationType.STRING, displayName = "Text")
public final class Characters implements StreamSource<Integer> {

	private String text = "Hello World!";

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public String getSourceCode() {
		return String.format("\"%s\".chars().boxed()", StreamOperation.quote(this.text));
	}

	private static final int MAX_PREVIEW_LENGTH = 50;

	@Override
	public String getConfigurationText() {
		return "text=" + this.text.substring(0, Math.min(MAX_PREVIEW_LENGTH, this.text.length()))
				+ (this.text.length() > MAX_PREVIEW_LENGTH ? "..." : "");
	}

	@Override
	public Stream<Integer> createStream() {
		return this.text.chars().boxed();
	}
}
