package name.ulbricht.streams.api;

import java.util.function.Function;
import java.util.stream.Stream;

public interface TerminalOperation<I> extends StreamOperation, Function<Stream<I>, Object> {
	// no members
}
