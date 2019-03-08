package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Name;

@Name("Java Script Mapper")
@Configuration(JavaScriptConfigurationPane.class)
public final class JavaScriptMapper extends JavaScriptOperation {

	public JavaScriptMapper() {
		this.script = "result = element.getClass().getSimpleName();";
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.map(this::scriptMapper);
	}

	private Object scriptMapper(final Object element) {
		final var bindings = this.engine.createBindings();
		bindings.put("element", element);
		this.engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

		try {
			this.engine.eval(this.script);
			return this.engine.getBindings(ScriptContext.ENGINE_SCOPE).get("result");
		} catch (final ScriptException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}
}
