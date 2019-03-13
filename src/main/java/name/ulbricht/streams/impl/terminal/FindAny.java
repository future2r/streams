package name.ulbricht.streams.impl.terminal;

import java.util.Optional;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;

@Operation(name = "Find Any")
public final class FindAny implements TerminalOperation<Object> {

	@Override
	public String getSourceCode() {
		return ".findAny()";
	}

	@Override
	public Optional<Object> apply(final Stream<Object> stream) {
		return stream.findAny();
	}
}
