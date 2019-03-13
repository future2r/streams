package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;
import static name.ulbricht.streams.impl.StringUtils.quote;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "String Joiner", type = TERMINAL, input = String.class)
@Configuration(name = "delimiter", type = ConfigurationType.STRING, displayName = "Delimiter")
@Configuration(name = "prefix", type = ConfigurationType.STRING, displayName = "Prefix")
@Configuration(name = "suffix", type = ConfigurationType.STRING, displayName = "Suffix")
public final class StringJoiner implements Function<Stream<String>, Object> {

	private String delimiter = ",";
	private String prefix = "{";
	private String suffix = "}";

	public String getDelimiter() {
		return this.delimiter;
	}

	public void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(final String suffix) {
		this.suffix = suffix;
	}

	@Override
	public Object apply(final Stream<String> stream) {
		return stream.collect(Collectors.joining(this.delimiter, this.prefix, this.suffix));
	}

	@Override
	public String toString() {
		return String.format(".collect(Collectors.joining(\"%s\", \"%s\", \"%s\"))", quote(this.delimiter),
				quote(this.prefix), quote(this.suffix));
	}
}
