package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.TerminalOperation;

@Name("String Joiner")
@Input(String.class)
@Configuration(StringJoinerConfigurationPane.class)
public final class StringJoiner implements TerminalOperation<String> {

	private String delimiter = ",";
	private String prefix = "{";
	private String suffix = "}";

	String getDelimiter() {
		return this.delimiter;
	}

	void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

	String getPrefix() {
		return this.prefix;
	}

	void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	String getSuffix() {
		return this.suffix;
	}

	void setSuffix(final String suffix) {
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
