package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Map", type = INTERMEDIATE, description = "Returns a stream consisting of the results of applying the given function to the elements of this stream."
		+ " The current element is provided as 'element', the mapped result must stored in 'mapped'.")
public final class JavaScriptMap<I, O> extends JavaScriptOperation implements Function<Stream<I>, Stream<O>> {

	public JavaScriptMap() {
		super("mapped = element.getClass().getSimpleName();");
	}

	@Override
	public Stream<O> apply(final Stream<I> stream) {
		return stream.map(e -> evalScript(Map.of("element", e), "mapped"));
	}

	@Override
	public String toString() {
		return ".map( /* please check source code for JavaScript execution */)";
	}
}
