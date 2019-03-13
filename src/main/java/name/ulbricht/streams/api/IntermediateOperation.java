package name.ulbricht.streams.api;

import java.util.function.Function;
import java.util.stream.Stream;

public interface IntermediateOperation<I, O> extends StreamOperation, Function<Stream<I>, Stream<O>> {
	// no members
}
