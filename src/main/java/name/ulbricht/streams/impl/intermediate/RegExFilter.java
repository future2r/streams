package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;
import static name.ulbricht.streams.impl.StringUtils.quote;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Regular Expression Filter", type = INTERMEDIATE, input = String.class, output = String.class, description = "Returns a stream with only those elements of this stream, that pass a regular expression.")
public final class RegExFilter implements Function<Stream<String>, Stream<String>> {

	private String pattern = ".*";

	@Configuration(type = ConfigurationType.STRING, displayName = "Filter Pattern")
	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public Stream<String> apply(final Stream<String> stream) {
		return stream.filter(s -> Pattern.matches(this.pattern, s));
	}

	@Override
	public String toString() {
		return String.format(".filter(s -> Pattern.matches(\"%s\", s))", quote(this.pattern));
	}
}
