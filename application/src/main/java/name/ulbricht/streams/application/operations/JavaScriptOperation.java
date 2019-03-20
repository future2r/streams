package name.ulbricht.streams.application.operations;

import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import name.ulbricht.streams.application.ui.helper.StreamOperationException;

public abstract class JavaScriptOperation {

	private final static ScriptEngineManager engineManager = new ScriptEngineManager();

	private ScriptEngine engine;

	protected final void evalScript(final String script, final Map<String, Object> input) {
		evalScript(script, input, null);
	}

	@SuppressWarnings("unchecked")
	protected final <T> T evalScript(final String script, final Map<String, Object> input, final String resultName) {
		synchronized (engineManager) {
			if (this.engine == null)
				engine = engineManager.getEngineByMimeType("text/javascript");
		}

		final var bindings = this.engine.createBindings();
		bindings.putAll(input);
		this.engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

		try {
			this.engine.eval(script);
			if (resultName != null) {
				return (T) this.engine.getBindings(ScriptContext.ENGINE_SCOPE).get(resultName);
			}
			return null;
		} catch (final ScriptException ex) {
			throw new StreamOperationException(ex.getMessage(), ex);
		}
	}
}
