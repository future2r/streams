package name.ulbricht.streams.script;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream consisting of the results of replacing each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element.")
@Intermediate
public final class JavaScriptFlatMap<I, O> extends JavaScriptOperation implements Function<Stream<I>, Stream<O>> {
	
	private String script;

	public JavaScriptFlatMap() {
		this("mappedStream = element.toString().chars().boxed()");
	}

	public JavaScriptFlatMap(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@BeanProperty(description = "The current element is provided as 'element', the result must a Stream stored in 'mappedStream'.")
	public String getScript() {
		return this.script;
	}

	public void setScript(final String script) {
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
