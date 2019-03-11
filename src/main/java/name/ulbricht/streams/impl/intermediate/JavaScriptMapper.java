package name.ulbricht.streams.impl.intermediate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.impl.JavaScriptConfigurationPane;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Name("Java Script Mapper")
@Configuration(value = JavaScriptConfigurationPane.class, hint = "The current element is provided as 'element', the result must stored in 'result'.")
public final class JavaScriptMapper extends JavaScriptOperation implements IntermediateOperation<Object, Object> {

	public JavaScriptMapper() {
		super("result = element.getClass().getSimpleName();");
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
