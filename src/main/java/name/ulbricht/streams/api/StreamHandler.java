package name.ulbricht.streams.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class StreamHandler {

	protected final StreamSource<?> streamSource;
	protected final List<IntermediateOperation<?, ?>> intermediatOperations;
	protected final TerminalOperation<?> terminalOperation;

	public StreamHandler(final StreamSource<?> streamSource,
			final List<IntermediateOperation<?, ?>> intermediatOperations,
			final TerminalOperation<?> terminalOperation) {
		this.streamSource = Objects.requireNonNull(streamSource, "streamSource must not be null");
		this.intermediatOperations = new ArrayList<>(
				Objects.requireNonNull(intermediatOperations, "intermediatOperations must not be null"));
		this.terminalOperation = Objects.requireNonNull(terminalOperation, "terminalOperation must not be null");
	}
}
