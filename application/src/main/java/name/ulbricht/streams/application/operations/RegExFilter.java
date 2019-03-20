package name.ulbricht.streams.application.operations;

import static name.ulbricht.streams.application.ui.helper.StringUtils.quote;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream with only those elements of this stream, that pass a regular expression.")
@Intermediate
public final class RegExFilter implements Function<Stream<String>, Stream<String>> {

	private String pattern = ".*";

	@BeanProperty(description = "Regular expresstion to match")
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
