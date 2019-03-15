package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript For Each", type = TERMINAL, description = "Performs an action for each element of this stream. The current element is provided as 'element'.")
public final class JavaScriptForEach<T> extends JavaScriptOperation implements Function<Stream<T>, Void> {

	public JavaScriptForEach() {
		super("java.lang.System.out.println(element);");
	}

	@Override
	public Void apply(final Stream<T> stream) {
		stream.forEach(e -> evalScript(Map.of("element", e)));
		return null;
	}

	@Override
	public String toString() {
		return ".forEach( /* please check source code for JavaScript execution */)";
	}
}
