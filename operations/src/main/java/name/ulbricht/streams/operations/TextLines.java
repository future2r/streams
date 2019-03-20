package name.ulbricht.streams.operations;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.Source;

@JavaBean(description = "Splits a text into lines and returns them as a new stream.")
@Source
public final class TextLines implements Supplier<Stream<String>> {

	private String text = "Hello World!\n" // en
			+ "Hallo Welt!\n" // de
			+ "Salut tout le monde!\n" // fr
			+ "¡Hola mundo!\n" // es
			+ "Salve, mondo!\n" // it
			+ "qo' vIvan!"; // tlh

	@BeanProperty(description = "Lines of text to create the stream elements")
	@EditorHint(EditorType.MULTILINE_TEXT)
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
