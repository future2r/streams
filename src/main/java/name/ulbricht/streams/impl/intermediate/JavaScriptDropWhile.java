package name.ulbricht.streams.impl.intermediate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.impl.JavaScriptConfigurationPane;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Name("JavaScript Drop While")
@Configuration(value = JavaScriptConfigurationPane.class, hint = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
public final class JavaScriptDropWhile extends JavaScriptOperation implements IntermediateOperation<Object, Object> {

	public JavaScriptDropWhile() {
		super("result = true;");
	}

	@Override
	public String getSourceCode() {
		return ".dropWhile( /* please check source code for JavaScript execution */)";
	}
	
	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.dropWhile(this::matches);
	}
	
	private boolean matches(final Object element) {
		final Map<String, Object> input = new HashMap<>();
		input.put("element", element);
		final var output = evalScript(input);

		final var result = output.get("result");
		if (result instanceof Boolean)
			return ((Boolean) result).booleanValue();
		else
			throw new RuntimeException("Variable 'result' of type boolean not found.");
	}
}
