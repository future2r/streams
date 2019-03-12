package name.ulbricht.streams.api;

import java.util.stream.Stream;

public interface SourceOperation<O> extends StreamOperation {

	Stream<O> createStream();
}
