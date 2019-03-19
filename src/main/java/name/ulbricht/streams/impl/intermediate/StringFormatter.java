package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.ConfigurationType.STRING;
import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;
import static name.ulbricht.streams.impl.StringUtils.quote;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "String Formatter", type = INTERMEDIATE, output = String.class, description = "Formats the elements into a string using a pattern.")
public final class StringFormatter<T> implements Function<Stream<T>, Stream<String>> {

	private String format = "%s";

	@Configuration(type = STRING, displayName = "Format Pattern")
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
