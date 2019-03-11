package name.ulbricht.streams.impl.terminal;

import java.util.Optional;
import java.util.stream.Stream;

import name.ulbricht.streams.api.TerminalOperation;

public final class FindAny implements TerminalOperation<Object> {

	@Override
	public String getSourceCode() {
		return ".findAny()";
	}

	@Override
	public Optional<Object> terminateStream(final Stream<Object> stream) {
		return stream.findAny();
	}
}
