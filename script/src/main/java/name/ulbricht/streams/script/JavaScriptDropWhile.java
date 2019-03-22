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

@JavaBean(description = "Returns, if this stream is ordered, a stream consisting of the remaining elements of this stream after dropping the longest prefix of elements that match the given predicate."
		+ " Otherwise returns, if this stream is unordered, a stream consisting of the remaining elements of this stream after dropping a subset of elements that match the given predicate.")
@Intermediate
public final class JavaScriptDropWhile<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	private String script;

	public JavaScriptDropWhile() {
		this("drop = true;");
	}

	public JavaScriptDropWhile(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@BeanProperty(description = "The current element is provided as 'element', the result must a boolean value stored in 'drop'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getScript() {
		return this.script;
	}

	public void setScript(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.dropWhile(e -> evalScript(this.script, Map.of("element", e), "drop"));
	}

	@Override
	public String toString() {
		return ".dropWhile( /* please check source code for JavaScript execution */)";
	}
}
