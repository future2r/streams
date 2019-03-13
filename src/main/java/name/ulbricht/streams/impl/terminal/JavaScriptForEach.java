package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript For Each", type = TERMINAL, description = "The current element is provided as 'element'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptForEach extends JavaScriptOperation implements Function<Stream<Object>, Object> {

	public JavaScriptForEach() {
		super("java.lang.System.out.println(element);");
	}

	@Override
	public Object apply(final Stream<Object> stream) {
		stream.forEach(e -> evalScript(Map.of("element", e)));
		return null;
	}

	@Override
	public String toString() {
		return ".forEach( /* please check source code for JavaScript execution */)";
	}
}
