package name.ulbricht.streams.api;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public final class StreamExecutor extends StreamHandler {

	private static final Logger log = Logger.getLogger("name.ulbricht.streams");

	public StreamExecutor(final StreamSource<?> streamSource,
			final List<IntermediateOperation<?, ?>> intermediatOperations,
			final TerminalOperation<?> terminalOperation) {
		super(streamSource, intermediatOperations, terminalOperation);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object execute() {
		Stream stream = this.streamSource.createStream();
		stream = addLogging(stream, this.streamSource);

		for (var intermediatOperation : this.intermediatOperations) {
			stream = intermediatOperation.processStream(stream);
			stream = addLogging(stream, intermediatOperation);
		}

		return this.terminalOperation.terminateStream(stream);
	}

	private static Stream<?> addLogging(final Stream<?> stream, final StreamOperation operation) {
		final var operationName = StreamOperation.getName(operation);
		return stream.peek(e -> log.info(() -> String.format("%s: %s", operationName, e)));
	}
}
