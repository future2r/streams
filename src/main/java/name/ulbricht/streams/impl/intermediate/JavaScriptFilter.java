package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Name;

@Name("Java Script Filter")
@Configuration(JavaScriptConfigurationPane.class)
public final class JavaScriptFilter extends JavaScriptOperation {

	public JavaScriptFilter() {
		this.script = "result = true;";
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.filter(this::scriptFilter);
	}

	private boolean scriptFilter(final Object element) {
		final var bindings = this.engine.createBindings();
		bindings.put("element", element);
		this.engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

		try {
			this.engine.eval(this.script);
			final var result = this.engine.getBindings(ScriptContext.ENGINE_SCOPE).get("result");
			if (result instanceof Boolean)
				return ((Boolean) result).booleanValue();
			else
				throw new ScriptException("Variable 'result' of type Boolean not found.");
		} catch (final ScriptException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}
}
