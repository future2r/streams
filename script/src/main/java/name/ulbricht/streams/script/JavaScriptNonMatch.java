package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns whether no elements of this stream match the provided predicate.")
@Terminal
public final class JavaScriptNonMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

	private String script;

	public JavaScriptNonMatch() {
		this("matches = true;");
	}

	public JavaScriptNonMatch(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@BeanProperty(description = "The current element is provided as 'element', the result must a boolean value stored in 'matches'.")
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@Override
	public Boolean apply(final Stream<T> stream) {
		return stream.noneMatch(e -> evalScript(this.script, Map.of("element", e), "matches"));
	}

	@Override
	public String toString() {
		return ".noneMatch( /* please check source code for JavaScript execution */)";
	}
}
