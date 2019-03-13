package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript All Match", type = TERMINAL, description = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
public final class JavaScriptAllMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

	public JavaScriptAllMatch() {
		super("result = true;");
	}

	@Override
	public Boolean apply(final Stream<T> stream) {
		return stream.allMatch(e -> evalScript(Map.of("element", e)));
	}

	@Override
	public String toString() {
		return ".allMatch( /* please check source code for JavaScript execution */)";
	}
}
