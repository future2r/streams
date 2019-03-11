package name.ulbricht.streams.impl.intermediate;

import java.util.HashMap;
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
		super("result = true;");
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
		final Map<String, Object> input = new HashMap<>();
		input.put("element1", element1);
		input.put("element2", element2);
		final var output = evalScript(input);

		final var result = output.get("result");
		if (result instanceof Integer)
			return ((Integer) result).intValue();
		else
			throw new RuntimeException("Variable 'result' of type int not found.");
	}
}
