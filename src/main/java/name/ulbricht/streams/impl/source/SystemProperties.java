package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Creates a stream with the keys of all Java system properties.")
@StreamOperation(type = SOURCE)
public final class SystemProperties implements Supplier<Stream<Object>> {

	@Override
	public Stream<Object> get() {
		return System.getProperties().keySet().stream();
	}

	@Override
	public String toString() {
		return "System.getProperties().keySet().stream()";
	}
}
