package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Flat Map", type = INTERMEDIATE, description = "Returns a stream consisting of the results of replacing each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element. The current element is provided as 'element', the result must a Stream stored in 'mappedStream'.")
public final class JavaScriptFlatMap<I, O> extends JavaScriptOperation implements Function<Stream<I>, Stream<O>> {

	public JavaScriptFlatMap() {
		super("mappedStream = element.toString().chars().boxed()");
	}

	@Override
	public Stream<O> apply(final Stream<I> stream) {
		return stream.flatMap(e -> evalScript(Map.of("element", e), "mappedStream"));
	}

	@Override
	public String toString() {
		return ".flatMap( /* please check source code for JavaScript execution */)";
	}
}
