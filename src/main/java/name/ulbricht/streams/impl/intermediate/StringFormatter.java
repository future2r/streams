package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;

@Name("String Formatter")
@Output(String.class)
@Configuration(StringFormatterConfigurationPane.class)
public final class StringFormatter implements IntermediateOperation<Object, String> {

	private String format = "%s";

	String getFormat() {
		return this.format;
	}

	void setFormat(final String format) {
		this.format = format;
	}

	@Override
	public String getSourceCode() {
		return String.format(".map(e -> String.format(\"%s\", e))", quote(this.format));
	}

	@Override
	public String getConfigurationText() {
		return String.format("format='%s'", this.format);
	}

	@Override
	public Stream<String> processStream(final Stream<Object> stream) {
		return stream.map(e -> String.format(this.format, e));
	}
}
