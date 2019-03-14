package name.ulbricht.streams.impl;

import java.util.Map;
import java.util.Objects;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperationException;

public abstract class JavaScriptOperation {

	private final static ScriptEngineManager engineManager = new ScriptEngineManager();

	private String script;
	private ScriptEngine engine;

	public JavaScriptOperation(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
	}

	@Configuration(type = ConfigurationType.MULTILINE_STRING, displayName = "JavaScript code")
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	protected final void evalScript(final Map<String, Object> input) {
		evalScript(this.script, input, null);
	}

	protected final <T> T evalScript(final Map<String, Object> input, final String resultName) {
		return evalScript(this.script, input, resultName);
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
