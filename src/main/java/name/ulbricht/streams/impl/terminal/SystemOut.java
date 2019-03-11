package name.ulbricht.streams.impl.terminal;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;

@Operation(name = "System.out")
public final class SystemOut implements TerminalOperation<Object> {

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
