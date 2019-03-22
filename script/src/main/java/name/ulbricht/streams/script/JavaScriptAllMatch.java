package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns whether all elements of this stream match the provided predicate.")
@Terminal
public final class JavaScriptAllMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

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
		return stream.allMatch(e -> evalScript(this.script, Map.of("element", e), "matches"));
	}

	@Override
	public String toString() {
		return ".allMatch( /* please check source code for JavaScript execution */)";
	}
}