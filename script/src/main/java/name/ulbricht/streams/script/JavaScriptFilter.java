package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream consisting of the elements of this stream that match the given predicate.")
@Intermediate
public final class JavaScriptFilter<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	public JavaScriptFilter() {
		this("pass = true;");
	}

	public JavaScriptFilter(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	private String script;

	@BeanProperty(description = "The current element is provided as 'element', the result must a boolean value stored in 'pass'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.filter(e -> evalScript(this.script, Map.of("element", e), "pass"));
	}

	@Override
	public String toString() {
		return ".filter( /* please check source code for JavaScript execution */)";
	}
}
