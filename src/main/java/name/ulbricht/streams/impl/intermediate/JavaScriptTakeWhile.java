package name.ulbricht.streams.impl.intermediate;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.Intermediate;
import name.ulbricht.streams.impl.JavaScriptOperation;

@JavaBean(description = "Returns, if this stream is ordered, a stream consisting of the longest prefix of elements taken from this stream that match the given predicate."
		+ " Otherwise returns, if this stream is unordered, a stream consisting of a subset of elements taken from this stream that match the given predicate.")
@Intermediate
public final class JavaScriptTakeWhile<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	private String script = "take = true;";
	
	@BeanProperty(description = "The current element is provided as 'element', the result must a boolean value stored in 'take'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
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
