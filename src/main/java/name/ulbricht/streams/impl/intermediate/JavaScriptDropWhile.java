package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Drop While", type = INTERMEDIATE, description = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
public final class JavaScriptDropWhile<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	public JavaScriptDropWhile() {
		super("result = true;");
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.dropWhile(e -> evalScript(Map.of("element", e)));
	}

	@Override
	public String toString() {
		return ".dropWhile( /* please check source code for JavaScript execution */)";
	}
}
