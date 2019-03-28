package name.ulbricht.streams.extended;

import static name.ulbricht.streams.extended.StringUtils.quote;
import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Provides the characters of a string as a new stream.")
@Source
public final class Characters implements Supplier<Stream<Integer>> {

	private String text;

	public Characters() {
		this("Hello World!");
	}

	public Characters(final String text) {
		this.text = Objects.requireNonNull(text, "text must not be null");
	}

	@BeanProperty(description = "A text to get the characters from")
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = Objects.requireNonNull(text, "text must not be null");
		;
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
