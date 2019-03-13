package name.ulbricht.streams.impl.intermediate;

import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Operation(name = "JavaScript Drop While", description = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptDropWhile extends JavaScriptOperation implements IntermediateOperation<Object, Object> {

	public JavaScriptDropWhile() {
		super("result = true;");
	}

	@Override
	public String getSourceCode() {
		return ".dropWhile( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.dropWhile(e -> evalScript(Map.of("element", e)));
	}
}
