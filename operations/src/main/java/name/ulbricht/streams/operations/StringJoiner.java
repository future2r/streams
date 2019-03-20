package name.ulbricht.streams.operations;

import static name.ulbricht.streams.operations.StringUtils.quote;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Collects all strings in the stream into a single string.")
@Terminal
public final class StringJoiner implements Function<Stream<String>, String> {

	private String delimiter = ",";
	private String prefix = "{";
	private String suffix = "}";

	@BeanProperty(description = "Delimiter to separate the strings")
	public String getDelimiter() {
		return this.delimiter;
	}

	public void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

	@BeanProperty(description = "Prefix added once before the resulting text")
	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	@BeanProperty(description = "Suffix added once after the resulting text")
	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(final String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String apply(final Stream<String> stream) {
		return stream.collect(Collectors.joining(this.delimiter, this.prefix, this.suffix));
	}

	@Override
	public String toString() {
		return String.format(".collect(Collectors.joining(\"%s\", \"%s\", \"%s\"))", quote(this.delimiter),
				quote(this.prefix), quote(this.suffix));
	}
}
