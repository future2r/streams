package name.ulbricht.streams.impl.terminal;

import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Operation(name = "JavaScript AllMatch")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript", description = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
public final class JavaScriptAllMatch extends JavaScriptOperation implements TerminalOperation<Object> {

	public JavaScriptAllMatch() {
		super("result = true;");
	}

	@Override
	public String getSourceCode() {
		return ".allMatch( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Object terminateStream(final Stream<Object> stream) {
		return stream.allMatch(e -> evalScript(Map.of("element", e)));
	}
}
