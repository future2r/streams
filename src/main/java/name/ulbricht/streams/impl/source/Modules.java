package name.ulbricht.streams.impl.source;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.SourceOperation;

@Operation(name = "Modules List", output = Module.class)
public final class Modules implements SourceOperation<Module> {

	@Override
	public String getSourceCode() {
		return "ModuleLayer.boot().modules().stream()";
	}

	@Override
	public Stream<Module> get() {
		return ModuleLayer.boot().modules().stream();
	}
}
