package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Any Match", type = TERMINAL, description = "Returns whether any elements of this stream match the provided predicate. The current element is provided as 'element', the result must a boolean value stored in 'matches'.")
public final class JavaScriptAnyMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

	public JavaScriptAnyMatch() {
		super("matches = true;");
	}

	@Override
	public Boolean apply(final Stream<T> stream) {
		return stream.anyMatch(e -> evalScript(Map.of("element", e), "matches"));
	}

	@Override
	public String toString() {
		return ".anyMatch( /* please check source code for JavaScript execution */)";
	}
}
