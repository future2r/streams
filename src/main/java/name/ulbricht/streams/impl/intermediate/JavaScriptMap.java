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

@JavaBean(description = "Returns a stream consisting of the results of applying the given function to the elements of this stream.")
@Intermediate
public final class JavaScriptMap<I, O> extends JavaScriptOperation implements Function<Stream<I>, Stream<O>> {

	private String script = "mapped = element.getClass().getSimpleName();";
	
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
