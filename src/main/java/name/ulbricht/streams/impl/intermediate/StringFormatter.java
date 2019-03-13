package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.impl.StringUtils.quote;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(name = "String Formatter", output = String.class)
@Configuration(name = "format", type = ConfigurationType.STRING, displayName = "Format Pattern")
public final class StringFormatter implements IntermediateOperation<Object, String> {

	private String format = "%s";

	public String getFormat() {
		return this.format;
	}

	public void setFormat(final String format) {
		this.format = format;
	}

	@Override
	public String getSourceCode() {
		return String.format(".map(e -> String.format(\"%s\", e))", quote(this.format));
	}

	@Override
	public Stream<String> processStream(final Stream<Object> stream) {
		return stream.map(e -> String.format(this.format, e));
	}
}
