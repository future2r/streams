package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;
import static name.ulbricht.streams.impl.StringUtils.quote;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Regular Expression Splitter", type = INTERMEDIATE, input = String.class, output = String.class)
@Configuration(name = "pattern", type = ConfigurationType.STRING, displayName = "Split Pattern")
public final class RegExSplitter implements Function<Stream<String>, Stream<String>> {

	private String pattern = "\\s";

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public Stream<String> apply(final Stream<String> stream) {
		return stream.flatMap(s -> Stream.of(s.split(this.pattern)));
	}

	@Override
	public String toString() {
		return String.format(".flatMap(s -> Stream.of(s.split(\"%s\")))", quote(this.pattern));
	}
}
