package name.ulbricht.streams.api;

import java.util.stream.Stream;

public interface TerminalOperation<I> extends StreamOperation {

	Object terminateStream(final Stream<I> stream);
}
