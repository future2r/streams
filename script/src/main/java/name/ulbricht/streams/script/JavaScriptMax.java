package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Returns the maximum element of this stream according to the provided Comparator.")
@Terminal
public final class JavaScriptMax<T> extends JavaScriptOperation implements Function<Stream<T>, Optional<T>> {

	private String script;

	public JavaScriptMax() {
		this("result = java.util.Objects.compare(element1, element2, java.util.Comparator.naturalOrder());");
	}

	public JavaScriptMax(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@BeanProperty(description = "Two elements are provided as 'element1' and 'element2', the result must an int stored in 'result'.")
	public String getScript() {
		return this.script;
	}

	public void setScript(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@Override
	public Optional<T> apply(final Stream<T> stream) {
		return stream.max(this::compare);
	}

	private int compare(final Object element1, final Object element2) {
		return evalScript(this.script, Map.of("element1", element1, "element2", element2), "result");
	}

	@Override
	public String toString() {
		return ".max( /* please check source code for JavaScript execution */)";
	}
}
