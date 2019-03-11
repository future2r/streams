package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;

@Name("Regular Expression Filter")
@Input(String.class)
@Output(String.class)
@Configuration(name = "pattern", type = ConfigurationType.STRING, displayName = "Filter Pattern")
public final class RegExFilter implements IntermediateOperation<String, String> {

	private String pattern = ".*";

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String getSourceCode() {
		return String.format(".filter(s -> Pattern.matches(\"%s\", s))", quote(this.pattern));
	}

	@Override
	public String getConfigurationText() {
		return String.format("pattern='%s'", this.pattern);
	}

	@Override
	public Stream<String> processStream(final Stream<String> stream) {
		return stream.filter(s -> Pattern.matches(this.pattern, s));
	}
}
