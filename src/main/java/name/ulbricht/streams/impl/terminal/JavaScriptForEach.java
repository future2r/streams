package name.ulbricht.streams.impl.terminal;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.TerminalOperation;
import name.ulbricht.streams.impl.JavaScriptConfigurationPane;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Name("JavaScript ForEach")
@Configuration(value = JavaScriptConfigurationPane.class, hint = "The current element is provided as 'element'.")
public final class JavaScriptForEach extends JavaScriptOperation implements TerminalOperation<Object> {

	public JavaScriptForEach() {
		super("java.lang.System.out.println(element);");
	}

	@Override
	public String getSourceCode() {
		return ".forEach( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Object terminateStream(final Stream<Object> stream) {
		stream.forEach(this::consume);
		return null;
	}

	private void consume(final Object element) {
		final Map<String, Object> input = new HashMap<>();
		input.put("element", element);
		evalScript(input);
	}
}
