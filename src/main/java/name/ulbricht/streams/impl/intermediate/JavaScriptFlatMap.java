package name.ulbricht.streams.impl.intermediate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Name("JavaScript FlatMap")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript", description = "The current element is provided as 'element', the result must a Stream stored in 'result'.")
public final class JavaScriptFlatMap extends JavaScriptOperation implements IntermediateOperation<Object, Object> {

	public JavaScriptFlatMap() {
		super("result = element.toString().chars().boxed()");
	}

	@Override
	public String getSourceCode() {
		return ".flatMap( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.flatMap(this::flatMap);
	}

	@SuppressWarnings("unchecked")
	private Stream<Object> flatMap(final Object element) {
		final Map<String, Object> input = new HashMap<>();
		input.put("element", element);
		final var output = evalScript(input);

		final var result = output.get("result");
		if (result instanceof Stream)
			return ((Stream<Object>) result);
		else
			throw new RuntimeException("Variable 'result' of type Stream not found.");
	}
}
