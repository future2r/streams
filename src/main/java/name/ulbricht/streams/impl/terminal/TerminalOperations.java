package name.ulbricht.streams.impl.terminal;

import name.ulbricht.streams.api.TerminalOperation;

public final class TerminalOperations {

	@SuppressWarnings("unchecked")
	public static final Class<? extends TerminalOperation<?>>[] IMPLEMENTATIONS = new Class[] { SystemOut.class,
			Count.class, Min.class, Max.class, FindFirst.class, FindAny.class, ToArray.class, ListCollector.class,
			JavaScriptAnyMatch.class, JavaScriptAllMatch.class, StringJoiner.class, StringLengthGrouping.class,
			TextFileWriter.class };

	private TerminalOperations() {
		// hidden
	}
}
