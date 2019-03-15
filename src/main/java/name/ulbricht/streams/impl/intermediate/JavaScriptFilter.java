package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Filter", type = INTERMEDIATE, description = "Returns a stream consisting of the elements of this stream that match the given predicate. The current element is provided as 'element', the result must a boolean value stored in 'pass'.")
public final class JavaScriptFilter<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	public JavaScriptFilter() {
		super("pass = true;");
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.filter(e -> evalScript(Map.of("element", e), "pass"));
	}

	@Override
	public String toString() {
		return ".filter( /* please check source code for JavaScript execution */)";
	}
}
