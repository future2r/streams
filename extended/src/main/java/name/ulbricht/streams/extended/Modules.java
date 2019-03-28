package name.ulbricht.streams.extended;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Creates a stream with all modules currently available.")
@Source
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
