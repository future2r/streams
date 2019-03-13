package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Text Lines", type = SOURCE, output = String.class)
public final class TextLines implements Supplier<Stream<String>> {

	private String text = "Hello World!\n" // en
			+ "Hallo Welt!\n" // de
			+ "Salut tout le monde!\n" // fr
			+ "¡Hola mundo!\n" // es
			+ "Salve, mondo!\n" // it
			+ "qo' vIvan!"; // tlh

	@Configuration(type = ConfigurationType.MULTILINE_STRING, displayName = "Text Lines")
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public Stream<String> get() {
		return this.text.lines();
	}

	@Override
	public String toString() {
		return this.text.lines().map(l -> "\"" + l + "\\n\"").collect(Collectors.joining("\n + ", "", ".lines()"));
	}
}
