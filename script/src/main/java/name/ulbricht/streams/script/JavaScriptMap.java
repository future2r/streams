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

@JavaBean(description = "Returns a stream consisting of the results of applying the given function to the elements of this stream.")
@Intermediate
public final class JavaScriptMap<I, O> extends JavaScriptOperation implements Function<Stream<I>, Stream<O>> {
	
	private String script;

	public JavaScriptMap() {
		this("mapped = element.getClass().getSimpleName();");
	}

	public JavaScriptMap(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@BeanProperty(description = "The current element is provided as 'element', the mapped result must stored in 'mapped'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public Stream<O> apply(final Stream<I> stream) {
		return stream.map(e -> evalScript(this.script, Map.of("element", e), "mapped"));
	}

	@Override
	public String toString() {
		return ".map( /* please check source code for JavaScript execution */)";
	}
}
