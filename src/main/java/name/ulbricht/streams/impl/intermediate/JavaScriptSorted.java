package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Sorted", type = INTERMEDIATE, description = "Two elements are provided as 'element1' and 'element2', the result must an int stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptSorted extends JavaScriptOperation implements Function<Stream<Object>, Stream<Object>> {

	public JavaScriptSorted() {
		super("result = java.util.Objects.compare(element1, element2, java.util.Comparator.naturalOrder());");
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.sorted(this::compare);
	}

	private int compare(final Object element1, final Object element2) {
		return evalScript(Map.of("element1", element1, "element2", element2));
	}

	@Override
	public String toString() {
		return ".sorted( /* please check source code for JavaScript execution */)";
	}
}
