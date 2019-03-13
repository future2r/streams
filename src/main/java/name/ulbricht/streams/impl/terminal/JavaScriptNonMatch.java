package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperationType.TERMINAL;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript None Match", type = TERMINAL, description = "The current element is provided as 'element', the result must a boolean value stored in 'result'.")
@Configuration(name = "script", type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript")
public final class JavaScriptNonMatch<T> extends JavaScriptOperation implements Function<Stream<T>, Boolean> {

	public JavaScriptNonMatch() {
		super("result = true;");
	}

	@Override
	public Boolean apply(final Stream<T> stream) {
		return stream.noneMatch(e -> evalScript(Map.of("element", e)));
	}

	@Override
	public String toString() {
		return ".noneMatch( /* please check source code for JavaScript execution */)";
	}
}
