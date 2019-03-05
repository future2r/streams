package name.ulbricht.streams.impl.terminal;

import name.ulbricht.streams.api.TerminalOperation;

public final class TerminalOperations {

	@SuppressWarnings("unchecked")
	public static final Class<? extends TerminalOperation<?>>[] IMPLEMENTATIONS = new Class[] { SystemOutConsumer.class,
			Count.class, Min.class, Max.class, ToArray.class, ListCollector.class, StringJoiner.class,
			TextFileWriter.class };

	private TerminalOperations() {
		// hidden
	}
}
