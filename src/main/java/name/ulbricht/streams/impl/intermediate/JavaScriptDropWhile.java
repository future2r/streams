package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Drop While", type = INTERMEDIATE, description = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptDropWhile extends JavaScriptOperation implements Function<Stream<Object>, Stream<Object>> {

	public JavaScriptDropWhile() {
		super("result = true;");
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.dropWhile(e -> evalScript(Map.of("element", e)));
	}

	@Override
	public String toString() {
		return ".dropWhile( /* please check source code for JavaScript execution */)";
	}
}
