package name.ulbricht.streams.impl.terminal;

import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Operation(name = "JavaScript AnyMatch")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript", description = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
public final class JavaScriptAnyMatch extends JavaScriptOperation implements TerminalOperation<Object> {

	public JavaScriptAnyMatch() {
		super("result = true;");
	}

	@Override
	public String getSourceCode() {
		return ".anyMatch( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Object terminateStream(final Stream<Object> stream) {
		return stream.anyMatch(e -> evalScript(Map.of("element", e)));
	}
}
