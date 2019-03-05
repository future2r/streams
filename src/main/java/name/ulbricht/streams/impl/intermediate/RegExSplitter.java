package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;

@Name("Regular Expression Splitter")
@Input(String.class)
@Output(String.class)
@Configuration(RegExSplitterConfigurationPane.class)
public final class RegExSplitter implements IntermediateOperation<String, String> {

	private String pattern = "\\s";

	String getPattern() {
		return this.pattern;
	}

	void setPattern(final String pattern) {
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
