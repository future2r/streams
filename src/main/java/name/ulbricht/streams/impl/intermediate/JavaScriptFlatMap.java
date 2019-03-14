package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Flat Map", type = INTERMEDIATE, description = "The current element is provided as 'element', the result must a Stream stored in 'mappedStream'.")
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
