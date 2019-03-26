package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns whether any elements of this stream match the provided predicate.")
@Terminal
public final class JavaScriptAnyMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

	private String script;

	public JavaScriptAnyMatch() {
		this("matches = true;");
	}

	public JavaScriptAnyMatch(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}
	
	@BeanProperty(description = "The current element is provided as 'element', the result must a boolean value stored in 'matches'.")
	public String getScript() {
		return this.script;
	}

	public void setScript(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
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
