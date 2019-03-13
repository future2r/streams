package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Take While", type = INTERMEDIATE, description = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptTakeWhile<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	public JavaScriptTakeWhile() {
		super("result = true;");
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.takeWhile(e -> evalScript(Map.of("element", e)));
	}

	@Override
	public String toString() {
		return ".takeWhile( /* please check source code for JavaScript execution */)";
	}
}
