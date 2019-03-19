package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.beans.JavaBean;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@JavaBean(description = "Returns whether no elements of this stream match the provided predicate."
		+ " The current element is provided as 'element', the result must a boolean value stored in 'matches'.")
@StreamOperation(type = TERMINAL)
public final class JavaScriptNonMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

	public JavaScriptNonMatch() {
		super("matches = true;");
	}

	@Override
	public Boolean apply(final Stream<T> stream) {
		return stream.noneMatch(e -> evalScript(Map.of("element", e), "matches"));
	}

	@Override
	public String toString() {
		return ".noneMatch( /* please check source code for JavaScript execution */)";
	}
}
