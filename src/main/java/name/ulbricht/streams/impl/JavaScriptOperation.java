package name.ulbricht.streams.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import name.ulbricht.streams.api.StreamOperation;

public abstract class JavaScriptOperation implements StreamOperation {

	private String script;
	private final ScriptEngineManager engineManager;
	private final ScriptEngine engine;

	public JavaScriptOperation(final String script) {
		this.script = Objects.requireNonNull(script, "script must not be null");
		this.engineManager = new ScriptEngineManager();
		this.engine = this.engineManager.getEngineByMimeType("text/javascript");
	}

	public final String getScript() {
		return this.script;
	}

	public final void setScript(String script) {
		this.script = script;
	}

	@Override
	public final String getSourceCode() {
		return "// please check source code for details";
	}

	private static final int MAX_PREVIEW_LENGTH = 50;

	@Override
	public final String getConfigurationText() {
		return "script=" + this.script.substring(0, Math.min(MAX_PREVIEW_LENGTH, this.script.length()))
				+ (this.script.length() > MAX_PREVIEW_LENGTH ? "..." : "");
	}

	protected Map<String, Object> evalScript(final Map<String, Object> input) {
		final var bindings = this.engine.createBindings();
		bindings.putAll(input);
		this.engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

		try {
			this.engine.eval(this.script);
			return new HashMap<>(this.engine.getBindings(ScriptContext.ENGINE_SCOPE));
		} catch (final ScriptException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}
}
