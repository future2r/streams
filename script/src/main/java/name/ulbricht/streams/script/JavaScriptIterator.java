package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.Source;

@JavaBean(description = "Returns an infinite sequential ordered Stream produced by iterative application of a function to an initial element.")
@Source
public final class JavaScriptIterator<T> extends JavaScriptOperation implements Supplier<Stream<T>> {

	private String initialScript;
	private String hasNextScript;
	private String nextScript;

	public JavaScriptIterator() {
		this("seed = 1", "hasNext = element <= 42", "next = element + 1");
	}

	public JavaScriptIterator(final String initialScript, final String hasNextScript, final String nextScript) {
		this.initialScript = Objects.requireNonNull(initialScript, "initialScript must not be null");
		this.hasNextScript = Objects.requireNonNull(hasNextScript, "inithasNextScriptialScript must not be null");
		this.nextScript = Objects.requireNonNull(nextScript, "nextScript must not be null");
	}

	@BeanProperty(description = "This script must store the initial value in 'seed'")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getInitialScript() {
		return this.initialScript;
	}

	public void setInitialScript(final String initialScript) {
		this.initialScript = Objects.requireNonNull(initialScript, "initialScript must not be null");
	}

	@BeanProperty(description = "Depending on the current element (provided as 'element'), this script must return a boolean value stored in 'hasNext'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getHasNextScript() {
		return this.hasNextScript;
	}

	public void setHasNextScript(final String hasNextScript) {
		this.hasNextScript = Objects.requireNonNull(hasNextScript, "inithasNextScriptialScript must not be null");
	}

	@BeanProperty(description = "This script must store the next element 'next', depending on the current element provided as 'element'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getNextScript() {
		return this.nextScript;
	}

	public void setNextScript(final String nextScript) {
		this.nextScript = Objects.requireNonNull(nextScript, "nextScript must not be null");
	}

	@Override
	public Stream<T> get() {
		return Stream.iterate(evalScript(this.initialScript, Map.of(), "seed"),
				e -> evalScript(this.hasNextScript, Map.of("element", e), "hasNext"),
				e -> evalScript(this.nextScript, Map.of("element", e), "next"));
	}

	@Override
	public String toString() {
		return "Stream.iterate( /* please check source code for JavaScript execution */)";
	}
}
