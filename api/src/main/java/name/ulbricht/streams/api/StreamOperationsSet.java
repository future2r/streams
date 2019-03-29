package name.ulbricht.streams.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class StreamOperationsSet {

	private final Object source;
	private final List<Object> intermediats;
	private final Object terminal;

	public StreamOperationsSet(final Object source, final List<Object> intermediates, final Object terminal) {
		this.source = Objects.requireNonNull(source, "source must not be null");
		this.intermediats = new ArrayList<>(Objects.requireNonNull(intermediates, "intermediates must not be null"));
		this.terminal = Objects.requireNonNull(terminal, "terminal must not be null");
	}

	public Object getSource() {
		return this.source;
	}

	public List<Object> getIntermediats() {
		return Collections.unmodifiableList(this.intermediats);
	}

	public Object getTerminal() {
		return this.terminal;
	}
}
