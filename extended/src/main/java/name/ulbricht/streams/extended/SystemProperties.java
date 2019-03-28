package name.ulbricht.streams.extended;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Creates a stream with the keys of all Java system properties.")
@Source
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
