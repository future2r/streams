package name.ulbricht.streams.impl.intermediate;

import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Operation(name = "JavaScript Map", description = "The current element is provided as 'element', the result must stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptMap extends JavaScriptOperation implements IntermediateOperation<Object, Object> {

	public JavaScriptMap() {
		super("result = element.getClass().getSimpleName();");
	}

	@Override
	public String getSourceCode() {
		return ".map( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.map(e -> evalScript(Map.of("element", e)));
	}
}
