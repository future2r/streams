package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript All Match", type = TERMINAL, description = "Returns whether all elements of this stream match the provided predicate. The current element is provided as 'element', the result must a boolean value stored in 'matches'.")
public final class JavaScriptAllMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

	public JavaScriptAllMatch() {
		super("matches = true;");
	}

	@Override
	public Boolean apply(final Stream<T> stream) {
		return stream.allMatch(e -> evalScript(Map.of("element", e), "matches"));
	}

	@Override
	public String toString() {
		return ".allMatch( /* please check source code for JavaScript execution */)";
	}
}
