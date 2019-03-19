package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Maximum", type = TERMINAL)
public final class JavaScriptMax<T> extends JavaScriptOperation implements Function<Stream<T>, Optional<T>> {

	public JavaScriptMax() {
		super("result = java.util.Objects.compare(element1, element2, java.util.Comparator.naturalOrder());");
	}

	@Override
	public Optional<T> apply(final Stream<T> stream) {
		return stream.max(this::compare);
	}

	private int compare(final Object element1, final Object element2) {
		return evalScript(Map.of("element1", element1, "element2", element2), "result");
	}

	@Override
	public String toString() {
		return ".max( /* please check source code for JavaScript execution */)";
	}
}
