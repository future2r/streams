package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream consisting of the elements of this stream, sorted according to the provided Comparator.")
@Intermediate
public final class JavaScriptSorted<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	private String script;

	public JavaScriptSorted() {
		this("result = java.util.Objects.compare(element1, element2, java.util.Comparator.naturalOrder());");
	}

	public JavaScriptSorted(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@BeanProperty(description = "Two elements are provided as 'element1' and 'element2', the result must an int stored in 'result'.")
	public String getScript() {
		return this.script;
	}

	public void setScript(final String script) {
		this.script = script;
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.sorted(this::compare);
	}

	private int compare(final Object element1, final Object element2) {
		return evalScript(this.script, Map.of("element1", element1, "element2", element2), "result");
	}

	@Override
	public String toString() {
		return ".sorted( /* please check source code for JavaScript execution */)";
	}
}
