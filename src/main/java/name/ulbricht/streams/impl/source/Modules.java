package name.ulbricht.streams.impl.source;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Output(Module.class)
public final class Modules implements StreamSource<Module> {

	@Override
	public String getSourceCode() {
		return "ModuleLayer.boot().modules().stream()";
	}

	@Override
	public Stream<Module> createStream() {
		return ModuleLayer.boot().modules().stream();
	}
}
