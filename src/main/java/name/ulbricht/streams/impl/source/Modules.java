package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Modules List", type = SOURCE, output = Module.class)
public final class Modules implements Supplier<Stream<Module>> {

	@Override
	public Stream<Module> get() {
		return ModuleLayer.boot().modules().stream();
	}

	@Override
	public String toString() {
		return "ModuleLayer.boot().modules().stream()";
	}
}
