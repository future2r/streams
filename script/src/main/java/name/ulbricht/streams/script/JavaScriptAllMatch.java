package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns whether all elements of this stream match the provided predicate.")
@Terminal
public final class JavaScriptAllMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

	private String script;

	public JavaScriptAllMatch() {
		this("matches = true;");
	}

	public JavaScriptAllMatch(final String script) {
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
		return stream.allMatch(e -> evalScript(this.script, Map.of("element", e), "matches"));
	}

	@Override
	public String toString() {
		return ".allMatch( /* please check source code for JavaScript execution */)";
	}
}
