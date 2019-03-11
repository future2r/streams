package name.ulbricht.streams.impl.terminal;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.TerminalOperation;
import name.ulbricht.streams.impl.JavaScriptConfigurationPane;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Name("JavaScript Max")
@Configuration(value = JavaScriptConfigurationPane.class, hint = "\"Two elements are provided as 'element1' and 'element2', the result must an int stored in 'result'.\"")
public final class JavaScriptMax extends JavaScriptOperation implements TerminalOperation<Object> {

	public JavaScriptMax() {
		super("result = java.util.Objects.compare(element1, element2, java.util.Comparator.naturalOrder());");
	}
	
	@Override
	public String getSourceCode() {
		return ".max( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Object terminateStream(final Stream<Object> stream) {
		return stream.max(this::compare);
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
