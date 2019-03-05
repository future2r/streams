package name.ulbricht.streams.api;

import java.util.stream.Stream;

public interface IntermediateOperation<I, O> extends StreamOperation {

	Stream<O> processStream(final Stream<I> stream);
}
