package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@JavaBean(description = "Returns whether any elements of this stream match the provided predicate.")
@StreamOperation(type = TERMINAL)
public final class JavaScriptAnyMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

	private String script = "matches = true;";
	
	@BeanProperty(description = "The current element is provided as 'element', the result must a boolean value stored in 'matches'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public Boolean apply(final Stream<T> stream) {
		return stream.anyMatch(e -> evalScript(this.script, Map.of("element", e), "matches"));
	}

	@Override
	public String toString() {
		return ".anyMatch( /* please check source code for JavaScript execution */)";
	}
}
