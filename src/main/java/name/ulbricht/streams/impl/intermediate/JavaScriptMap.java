package name.ulbricht.streams.impl.intermediate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Operation(name = "JavaScript Map")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript", description = "The current element is provided as 'element', the result must stored in 'result'.")
public final class JavaScriptMap extends JavaScriptOperation implements IntermediateOperation<Object, Object> {

	public JavaScriptMap() {
		super("result = element.getClass().getSimpleName();");
	}

	@Override
	public String getSourceCode() {
		return ".map( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.map(this::map);
	}

	private Object map(final Object element) {
		final Map<String, Object> input = new HashMap<>();
		input.put("element", element);
		final var output = evalScript(input);

		return output.get("result");
	}
}
