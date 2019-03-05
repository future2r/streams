package name.ulbricht.streams.api;

import java.util.stream.Stream;

public interface StreamSource<O> extends StreamOperation {

	Stream<O> createStream();
}
