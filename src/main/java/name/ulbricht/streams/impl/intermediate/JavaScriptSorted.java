package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@JavaBean(description = "Returns a stream consisting of the elements of this stream, sorted according to the provided Comparator.")
@StreamOperation(type = INTERMEDIATE)
public final class JavaScriptSorted<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	private String script = "result = java.util.Objects.compare(element1, element2, java.util.Comparator.naturalOrder());";
	
	@BeanProperty(description = "Two elements are provided as 'element1' and 'element2', the result must an int stored in 'result'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
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
