package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Creates a stream with all modules currently available.")
@StreamOperation(type = SOURCE, output = Module.class)
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
