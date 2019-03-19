package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.ConfigurationType.MULTILINE_STRING;
import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@StreamOperation(name = "JavaScript Iterator", type = SOURCE)
public final class JavaScriptIterator extends JavaScriptOperation implements Supplier<Stream<Object>> {

	private String hasNextScript = "hasNext = element <= 42";
	private String nextScript = "next = element + 1";

	public JavaScriptIterator() {
		super("seed = 1");
	}

	@Configuration(type = MULTILINE_STRING, displayName = "JavaScript code for inital value", ordinal = 0)
	@Override
	public String getScript() {
		return super.getScript();
	}

	@Configuration(type = MULTILINE_STRING, displayName = "JavaScript code if there is a next element", ordinal = 1)
	public String getHasNextScript() {
		return this.hasNextScript;
	}

	public void setHasNextScript(String hasNextScript) {
		this.hasNextScript = hasNextScript;
	}

	@Configuration(type = MULTILINE_STRING, displayName = "JavaScript code for next element", ordinal = 2)
	public String getNextScript() {
		return this.nextScript;
	}

	public void setNextScript(String nextScript) {
		this.nextScript = nextScript;
	}

	@Override
	public Stream<Object> get() {
		return Stream.iterate(evalScript(Map.of(), "seed"),
				e -> evalScript(this.hasNextScript, Map.of("element", e), "hasNext"),
				e -> evalScript(this.nextScript, Map.of("element", e), "next"));
	}

	@Override
	public String toString() {
		return "Stream.iterate( /* please check source code for JavaScript execution */)";
	}
}
