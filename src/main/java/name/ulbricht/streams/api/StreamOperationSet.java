package name.ulbricht.streams.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class StreamOperationSet {

	private final StreamSource<?> streamSource;
	private final List<IntermediateOperation<?, ?>> intermediatOperations;
	private final TerminalOperation<?> terminalOperation;

	public StreamOperationSet(final StreamSource<?> streamSource,
			final List<IntermediateOperation<?, ?>> intermediatOperations,
			final TerminalOperation<?> terminalOperation) {
		this.streamSource = Objects.requireNonNull(streamSource, "streamSource must not be null");
		this.intermediatOperations = new ArrayList<>(
				Objects.requireNonNull(intermediatOperations, "intermediatOperations must not be null"));
		this.terminalOperation = Objects.requireNonNull(terminalOperation, "terminalOperation must not be null");
	}

	public StreamSource<?> getStreamSource() {
		return this.streamSource;
	}

	public List<IntermediateOperation<?, ?>> getIntermediatOperations() {
		return Collections.unmodifiableList(this.intermediatOperations);
	}

	public TerminalOperation<?> getTerminalOperation() {
		return this.terminalOperation;
	}
}
