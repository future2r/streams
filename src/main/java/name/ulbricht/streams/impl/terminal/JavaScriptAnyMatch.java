package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Any Match", type = TERMINAL, description = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptAnyMatch extends JavaScriptOperation implements Function<Stream<Object>, Object> {

	public JavaScriptAnyMatch() {
		super("result = true;");
	}

	@Override
	public Object apply(final Stream<Object> stream) {
		return stream.anyMatch(e -> evalScript(Map.of("element", e)));
	}

	@Override
	public String toString() {
		return ".anyMatch( /* please check source code for JavaScript execution */)";
	}
}
