package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;

@Operation(name = "String Joiner", input = String.class)
@Configuration(name = "delimiter", type = ConfigurationType.STRING, displayName = "Delimiter")
@Configuration(name = "prefix", type = ConfigurationType.STRING, displayName = "Prefix")
@Configuration(name = "suffix", type = ConfigurationType.STRING, displayName = "Suffix")
public final class StringJoiner implements TerminalOperation<String> {

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
	public String getSourceCode() {
		return String.format(".collect(Collectors.joining(\"%s\", \"%s\", \"%s\"))", quote(this.delimiter),
				quote(this.prefix), quote(this.suffix));
	}

	@Override
	public String getConfigurationText() {
		return String.format("delimiter='%s', prefix='%s', suffix='%s'", this.delimiter, this.prefix, this.suffix);
	}

	@Override
	public Object terminateStream(final Stream<String> stream) {
		return stream.collect(Collectors.joining(this.delimiter, this.prefix, this.suffix));
	}
}
