package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(name = "Regular Expression Splitter", input = String.class, output = String.class)
@Configuration(name = "pattern", type = ConfigurationType.STRING, displayName = "Split Pattern")
public final class RegExSplitter implements IntermediateOperation<String, String> {

	private String pattern = "\\s";

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String getSourceCode() {
		return String.format(".flatMap(s -> Stream.of(s.split(\"%s\")))", quote(this.pattern));
	}

	@Override
	public String getConfigurationText() {
		return String.format("pattern='%s'", this.pattern);
	}

	@Override
	public Stream<String> processStream(final Stream<String> stream) {
		return stream.flatMap(s -> Stream.of(s.split(this.pattern)));
	}
}
