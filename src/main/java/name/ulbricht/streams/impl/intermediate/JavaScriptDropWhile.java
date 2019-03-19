package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.beans.JavaBean;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@JavaBean(description = "Returns, if this stream is ordered, a stream consisting of the remaining elements of this stream after dropping the longest prefix of elements that match the given predicate."
		+ " Otherwise returns, if this stream is unordered, a stream consisting of the remaining elements of this stream after dropping a subset of elements that match the given predicate."
		+ " The current element is provided as 'element', the result must a boolean value stored in 'drop'.")
@StreamOperation(type = INTERMEDIATE)
public final class JavaScriptDropWhile<T> extends JavaScriptOperation implements Function<Stream<T>, Stream<T>> {

	public JavaScriptDropWhile() {
		super("drop = true;");
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.dropWhile(e -> evalScript(Map.of("element", e), "drop"));
	}

	@Override
	public String toString() {
		return ".dropWhile( /* please check source code for JavaScript execution */)";
	}
}
