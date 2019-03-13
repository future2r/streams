package name.ulbricht.streams.api;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface SourceOperation<O> extends StreamOperation, Supplier<Stream<O>> {
	// no members
}
