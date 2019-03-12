package name.ulbricht.streams.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class StreamOperationSet {

	private final SourceOperation<?> source;
	private final List<IntermediateOperation<?, ?>> intermediats;
	private final TerminalOperation<?> terminal;

	public StreamOperationSet(final SourceOperation<?> source, final List<IntermediateOperation<?, ?>> intermediats,
			final TerminalOperation<?> terminal) {
		this.source = Objects.requireNonNull(source, "source must not be null");
		this.intermediats = new ArrayList<>(Objects.requireNonNull(intermediats, "intermediat must not be null"));
		this.terminal = Objects.requireNonNull(terminal, "terminal must not be null");
	}

	public SourceOperation<?> getSource() {
		return this.source;
	}

	public List<IntermediateOperation<?, ?>> getIntermediats() {
		return Collections.unmodifiableList(this.intermediats);
	}

	public TerminalOperation<?> getTerminal() {
		return this.terminal;
	}
}
