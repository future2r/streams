package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "erforms an action for each element of this stream.")
@Terminal
public final class JavaScriptForEach<T> extends JavaScriptOperation implements Function<Stream<T>, Void> {

	private String script;

	public JavaScriptForEach() {
		this("java.lang.System.out.println(element);");
	}

	public JavaScriptForEach(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}
	
	@BeanProperty(description = "The current element is provided as 'element'.")
	public String getScript() {
		return this.script;
	}

	public void setScript(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@Override
	public Void apply(final Stream<T> stream) {
		stream.forEach(e -> evalScript(this.script, Map.of("element", e)));
		return null;
	}

	@Override
	public String toString() {
		return ".forEach( /* please check source code for JavaScript execution */)";
	}
}
