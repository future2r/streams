package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.impl.StringUtils.quote;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Uses a regular expression to split a string into multiple strings and returns them as a stream.")
@Intermediate
public final class RegExSplitter implements Function<Stream<String>, Stream<String>> {

	private String pattern = "\\s";

	@BeanProperty(description = "Regular expression for splitting")
	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public Stream<String> apply(final Stream<String> stream) {
		return stream.flatMap(s -> Stream.of(s.split(this.pattern)));
	}

	@Override
	public String toString() {
		return String.format(".flatMap(s -> Stream.of(s.split(\"%s\")))", quote(this.pattern));
	}
}
