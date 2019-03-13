package name.ulbricht.streams.impl.source;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.SourceOperation;

@Operation(name = "Java System Properties")
public final class SystemProperties implements SourceOperation<Object> {

	@Override
	public String getSourceCode() {
		return "System.getProperties().keySet().stream()";
	}

	@Override
	public Stream<Object> get() {
		return System.getProperties().keySet().stream();
	}
}
