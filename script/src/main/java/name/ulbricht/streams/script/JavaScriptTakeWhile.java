package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns, if this stream is ordered, a stream consisting of the longest prefix of elements taken from this stream that match the given predicate."
		+ " Otherwise returns, if this stream is unordered, a stream consisting of a subset of elements taken from this stream that match the given predicate.")
@Intermediate
public final class JavaScriptTakeWhile<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	private String script;

	public JavaScriptTakeWhile() {
		this("drop = true;");
	}

	public JavaScriptTakeWhile(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@BeanProperty(description = "The current element is provided as 'element', the result must a boolean value stored in 'take'.")
	public String getScript() {
		return this.script;
	}

	public void setScript(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.takeWhile(e -> evalScript(this.script, Map.of("element", e), "take"));
	}

	@Override
	public String toString() {
		return ".takeWhile( /* please check source code for JavaScript execution */)";
	}
}
