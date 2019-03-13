package name.ulbricht.streams.impl.terminal;

import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Operation(name = "JavaScript Maximum", description = "Two elements are provided as 'element1' and 'element2', the result must an int stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptMax extends JavaScriptOperation implements TerminalOperation<Object> {

	public JavaScriptMax() {
		super("result = java.util.Objects.compare(element1, element2, java.util.Comparator.naturalOrder());");
	}

	@Override
	public String getSourceCode() {
		return ".max( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Object apply(final Stream<Object> stream) {
		return stream.max(this::compare);
	}

	private int compare(final Object element1, final Object element2) {
		return evalScript(Map.of("element1", element1, "element2", element2));
	}
}
