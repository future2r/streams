package name.ulbricht.streams.impl;

import static name.ulbricht.streams.impl.StringUtils.omit;

import java.util.Map;
import java.util.Objects;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.api.StreamOperationException;

public abstract class JavaScriptOperation implements StreamOperation {

	private final static ScriptEngineManager engineManager = new ScriptEngineManager();

	private String script;
	private ScriptEngine engine;

	public JavaScriptOperation(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	public final String getScript() {
		return this.script;
	}

	public final void setScript(String script) {
		this.script = script;
	}

	@Override
	public final String getConfigurationText() {
		return omit(this.script, 50);
	}

	@SuppressWarnings("unchecked")
	protected <T> T evalScript(final Map<String, Object> input) {
		synchronized (engineManager) {
			if (this.engine == null)
				engine = engineManager.getEngineByMimeType("text/javascript");
		}

		final var bindings = this.engine.createBindings();
		bindings.putAll(input);
		this.engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

		try {
			this.engine.eval(this.script);
			return (T) this.engine.getBindings(ScriptContext.ENGINE_SCOPE).get("result");
		} catch (final ScriptException ex) {
			throw new StreamOperationException(ex.getMessage(), ex);
		}
	}
}
