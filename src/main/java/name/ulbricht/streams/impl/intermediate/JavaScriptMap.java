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
public final class JavaScriptMap<I, O> extends JavaScriptOperation implements Function<Stream<I>, Stream<O>> {

	public JavaScriptMap() {
		super("result = element.getClass().getSimpleName();");
	}

	@Override
	public Stream<O> apply(final Stream<I> stream) {
		return stream.map(e -> evalScript(Map.of("element", e)));
	}

	@Override
	public String toString() {
		return ".map( /* please check source code for JavaScript execution */)";
	}
}
