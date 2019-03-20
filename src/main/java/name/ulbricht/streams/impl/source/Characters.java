package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.impl.StringUtils.quote;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Provides the characters of a string as a new stream.")
@Source
public final class Characters implements Supplier<Stream<Integer>> {

	private String text = "Hello World!";

	@BeanProperty(description = "A text to get the characters from")
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
