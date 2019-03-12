package name.ulbricht.streams.impl.terminal;

import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Operation(name = "JavaScript ForEach")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript", description = "The current element is provided as 'element'.")
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
		stream.forEach(e->evalScript(Map.of("element", e)));
		return null;
	}
}
