package name.ulbricht.streams.impl.intermediate;

import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Operation(name = "JavaScript Sorted")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript", description = "Two elements are provided as 'element1' and 'element2', the result must an int stored in 'result'.")
public final class JavaScriptSorted extends JavaScriptOperation implements IntermediateOperation<Object, Object> {

	public JavaScriptSorted() {
		super("result = java.util.Objects.compare(element1, element2, java.util.Comparator.naturalOrder());");
	}

	@Override
	public String getSourceCode() {
		return ".sorted( /* please check source code for JavaScript execution */)";
	}
	
	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.sorted(this::compare);
	}
	
	private int compare(final Object element1, final Object element2) {
		return evalScript(Map.of("element1", element1, "element2", element2));
	}
}
