package name.ulbricht.streams.impl.intermediate;

import java.util.Map;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@Operation(name = "JavaScript FlatMap", description = "The current element is provided as 'element', the result must a Stream stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptFlatMap extends JavaScriptOperation implements IntermediateOperation<Object, Object> {

	public JavaScriptFlatMap() {
		super("result = element.toString().chars().boxed()");
	}

	@Override
	public String getSourceCode() {
		return ".flatMap( /* please check source code for JavaScript execution */)";
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.flatMap(e -> evalScript(Map.of("element", e)));
	}
}
