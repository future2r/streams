package name.ulbricht.streams.operations;

import static name.ulbricht.streams.operations.StringUtils.quote;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Formats the elements into a string using a pattern.")
@Intermediate
public final class StringFormatter<T> implements Function<Stream<T>, Stream<String>> {

	public StringFormatter() {
		this("%s");
	}

	public StringFormatter(final String format) {
		this.format = Objects.requireNonNull(format, "format must not be null");
	}

	private String format;

	@BeanProperty(description = "Pattern to be used with String.format()")
	public String getFormat() {
		return this.format;
	}

	public void setFormat(final String format) {
		this.format = format;
	}

	@Override
	public Stream<String> apply(final Stream<T> stream) {
		return stream.map(e -> String.format(this.format, e));
	}

	@Override
	public String toString() {
		return String.format(".map(e -> String.format(\"%s\", e))", quote(this.format));
	}
}