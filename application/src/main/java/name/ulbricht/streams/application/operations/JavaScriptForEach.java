package name.ulbricht.streams.application.operations;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "erforms an action for each element of this stream.")
@Terminal
public final class JavaScriptForEach<T> extends JavaScriptOperation implements Function<Stream<T>, Void> {

	private String script = "java.lang.System.out.println(element);";
	
	@BeanProperty(description = "The current element is provided as 'element'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
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
