package name.ulbricht.streams.impl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import name.ulbricht.streams.api.StreamOperation;

public abstract class JavaScriptOperation implements StreamOperation {

	protected String script;

	protected final ScriptEngineManager engineManager;
	protected final ScriptEngine engine;

	public JavaScriptOperation() {
		this.engineManager = new ScriptEngineManager();
		this.engine = this.engineManager.getEngineByMimeType("text/javascript");
	}

	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public String getSourceCode() {
		return "// please check source code for details";
	}

	private static final int MAX_PREVIEW_LENGTH = 50;

	@Override
	public String getConfigurationText() {
		return "script=" + this.script.substring(0, Math.min(MAX_PREVIEW_LENGTH, this.script.length()))
				+ (this.script.length() > MAX_PREVIEW_LENGTH ? "..." : "");
	}
}
