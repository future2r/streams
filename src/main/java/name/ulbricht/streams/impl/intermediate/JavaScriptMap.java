package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Map", type = INTERMEDIATE, description = "The current element is provided as 'element', the result must stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptMap extends JavaScriptOperation implements Function<Stream<Object>, Stream<Object>> {

	public JavaScriptMap() {
		super("result = element.getClass().getSimpleName();");
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.map(e -> evalScript(Map.of("element", e)));
	}

	@Override
	public String toString() {
		return ".map( /* please check source code for JavaScript execution */)";
	}
}
