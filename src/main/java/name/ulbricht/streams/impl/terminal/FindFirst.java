package name.ulbricht.streams.impl.terminal;

import java.util.Optional;
import java.util.stream.Stream;

import name.ulbricht.streams.api.TerminalOperation;

public final class FindFirst implements TerminalOperation<Object> {

	@Override
	public String getSourceCode() {
		return ".findFirst()";
	}

	@Override
	public Optional<Object> apply(final Stream<Object> stream) {
		return stream.findFirst();
	}
}
