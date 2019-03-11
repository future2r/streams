package name.ulbricht.streams.impl.source;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Name("Text Lines")
@Output(String.class)
@Configuration(name = "text", type = ConfigurationType.MULTILINE_STRING, displayName = "Text Lines")
public final class TextLines implements StreamSource<String> {

	private String text = "Hello World!\n" // en
			+ "Hallo Welt!\n" // de
			+ "Salut tout le monde!\n" // fr
			+ "¡Hola mundo!\n" // es
			+ "Salve, mondo!\n" // it
			+ "qo' vIvan!"; // tlh

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public String getSourceCode() {
		return this.text.lines().map(l -> "\"" + l + "\\n\"").collect(Collectors.joining("\n + ", "", ".lines()"));
	}

	private static final int MAX_PREVIEW_LENGTH = 50;

	@Override
	public String getConfigurationText() {
		return "text=" + this.text.substring(0, Math.min(MAX_PREVIEW_LENGTH, this.text.length()))
				+ (this.text.length() > MAX_PREVIEW_LENGTH ? "..." : "");
	}

	@Override
	public Stream<String> createStream() {
		return this.text.lines();
	}
}
