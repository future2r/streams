package name.ulbricht.streams.impl.terminal;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.TerminalOperation;

@Name("System.out")
public final class SystemOutConsumer implements TerminalOperation<Object> {

	@Override
	public String getSourceCode() {
		return ".forEach(System.out::println)";
	}

	@Override
	public Object terminateStream(final Stream<Object> stream) {
		stream.forEach(System.out::println);
		return null;
	}
}
