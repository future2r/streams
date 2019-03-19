package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.JavaScriptOperation;

@JavaBean(description = "Returns a stream consisting of the results of replacing each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element.")
@StreamOperation(type = INTERMEDIATE)
public final class JavaScriptFlatMap<I, O> extends JavaScriptOperation implements Function<Stream<I>, Stream<O>> {

	private String script = "mappedStream = element.toString().chars().boxed()";
	
	@BeanProperty(description = "The current element is provided as 'element', the result must a Stream stored in 'mappedStream'.")
	@EditorHint(EditorType.MULTILINE_TEXT)
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public Stream<O> apply(final Stream<I> stream) {
		return stream.flatMap(e -> evalScript(this.script, Map.of("element", e), "mappedStream"));
	}

	@Override
	public String toString() {
		return ".flatMap( /* please check source code for JavaScript execution */)";
	}
}
