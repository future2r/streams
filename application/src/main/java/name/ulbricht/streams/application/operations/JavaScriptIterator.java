package name.ulbricht.streams.application.operations;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.Source;

@JavaBean(description = "Returns an infinite sequential ordered Stream produced by iterative application of a function to an initial element.")
@Source
public final class JavaScriptIterator extends JavaScriptOperation implements Supplier<Stream<Object>> {

	private String initialScript = "seed = 1";
	private String hasNextScript = "hasNext = element <= 42";
	private String nextScript = "next = element + 1";

	@BeanProperty(description = "This script must store the initial value in 'seed'")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getInitialScript() {
		return this.initialScript;
	}

	public void setInitialScript(final String initialScript) {
		this.initialScript = initialScript;
	}

	@BeanProperty(description = "Depending on the current element (provided as 'element'), this script must return a boolean value stored in 'hasNext'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getHasNextScript() {
		return this.hasNextScript;
	}

	public void setHasNextScript(String hasNextScript) {
		this.hasNextScript = hasNextScript;
	}

	@BeanProperty(description = "This script must store the next element 'next', depending on the current element provided as 'element'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getNextScript() {
		return this.nextScript;
	}

	public void setNextScript(String nextScript) {
		this.nextScript = nextScript;
	}

	@Override
	public Stream<Object> get() {
		return Stream.iterate(evalScript(this.initialScript, Map.of(), "seed"),
				e -> evalScript(this.hasNextScript, Map.of("element", e), "hasNext"),
				e -> evalScript(this.nextScript, Map.of("element", e), "next"));
	}

	@Override
	public String toString() {
		return "Stream.iterate( /* please check source code for JavaScript execution */)";
	}
}
