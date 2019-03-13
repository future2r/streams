package name.ulbricht.streams.impl.terminal;

import java.util.stream.Stream;

import name.ulbricht.streams.api.TerminalOperation;

public final class ToArray implements TerminalOperation<Object> {

	@Override
	public String getSourceCode() {
		return ".toArray()";
	}

	@Override
	public Object apply(final Stream<Object> stream) {
		return stream.toArray();
	}
}
