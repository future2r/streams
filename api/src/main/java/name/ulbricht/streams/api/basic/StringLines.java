package name.ulbricht.streams.api.basic;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Splits a string into lines and returns them as a new stream.")
@Source
public final class StringLines implements Supplier<Stream<String>> {

	private String text;

	public StringLines() {
		this("Hello World!\n" // en
				+ "Hallo Welt!\n" // de
				+ "Salut tout le monde!\n" // fr
				+ "¡Hola mundo!\n" // es
				+ "Salve, mondo!\n" // it
				+ "qo' vIvan!" // tlh
		);
	}

	public StringLines(final String text) {
		this.text = Objects.requireNonNull(text, "text must not be null");
	}

	@BeanProperty(description = "Lines of text to create the stream elements")
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = Objects.requireNonNull(text, "text must not be null");
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
