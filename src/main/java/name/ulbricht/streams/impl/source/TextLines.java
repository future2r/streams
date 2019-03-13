package name.ulbricht.streams.impl.source;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.SourceOperation;

@Operation(name = "Text Lines", output = String.class)
@Configuration(name = "text", type = ConfigurationType.MULTILINE_STRING, displayName = "Text Lines")
public final class TextLines implements SourceOperation<String> {

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

	@Override
	public Stream<String> get() {
		return this.text.lines();
	}
}
