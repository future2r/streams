package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Take While", type = INTERMEDIATE, description = "Returns, if this stream is ordered, a stream consisting of the longest prefix of elements taken from this stream that match the given predicate."
		+ " Otherwise returns, if this stream is unordered, a stream consisting of a subset of elements taken from this stream that match the given predicate."
		+ " The current element is provided as 'element', the result must a boolean value stored in 'take'.")
public final class JavaScriptTakeWhile<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	public JavaScriptTakeWhile() {
		super("take = true;");
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.takeWhile(e -> evalScript(Map.of("element", e), "take"));
	}

	@Override
	public String toString() {
		return ".takeWhile( /* please check source code for JavaScript execution */)";
	}
}
