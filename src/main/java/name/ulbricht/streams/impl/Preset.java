package name.ulbricht.streams.impl;

import java.util.List;
import java.util.function.Supplier;

import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.impl.intermediate.Distinct;
import name.ulbricht.streams.impl.intermediate.FileLines;
import name.ulbricht.streams.impl.intermediate.LowerCase;
import name.ulbricht.streams.impl.intermediate.RegExSplitter;
import name.ulbricht.streams.impl.intermediate.Sorted;
import name.ulbricht.streams.impl.intermediate.StringMapper;
import name.ulbricht.streams.impl.source.Empty;
import name.ulbricht.streams.impl.source.FindFiles;
import name.ulbricht.streams.impl.source.RandomIntegerGenerator;
import name.ulbricht.streams.impl.source.TextFileReader;
import name.ulbricht.streams.impl.source.TextLines;
import name.ulbricht.streams.impl.terminal.Count;
import name.ulbricht.streams.impl.terminal.StringLengthGrouping;
import name.ulbricht.streams.impl.terminal.SystemOut;
import name.ulbricht.streams.impl.terminal.TextFileWriter;

public enum Preset {

	DEFAULT("Default", Preset::createDefault),

	SPLIT_WORDS("Word length statistics", Preset::createSplitWords),

	SORT_LINES("Sort lines in a file", Preset::createSortLines),

	COUNT_LINES("Count lines in all files", Preset::createCountLines),

	GENERATE_NUMBERS("Generate a file with sorted numbers", Preset::createGenerateNumbers);

	private final String displayName;
	private final Supplier<StreamOperationSet> operationsFactory;

	private Preset(final String displayName, final Supplier<StreamOperationSet> operationsFactory) {
		this.displayName = displayName;
		this.operationsFactory = operationsFactory;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public StreamOperationSet operations() {
		return this.operationsFactory.get();
	}

	private static StreamOperationSet createDefault() {
		return new StreamOperationSet(new Empty(), List.of(), new SystemOut());
	}

	private static StreamOperationSet createSplitWords() {
		final var source = new TextLines();
		source.setText(
				"Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. "
						+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
						+ "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
						+ "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

		return new StreamOperationSet(source, List.of(new RegExSplitter(), new LowerCase()),
				new StringLengthGrouping());
	}

	private static StreamOperationSet createSortLines() {
		return new StreamOperationSet(new TextFileReader(), List.of(new Distinct(), new Sorted()),
				new TextFileWriter());
	}

	private static StreamOperationSet createCountLines() {
		return new StreamOperationSet(new FindFiles(), List.of(new FileLines()), new Count());
	}

	private static StreamOperationSet createGenerateNumbers() {
		return new StreamOperationSet(new RandomIntegerGenerator(),
				List.of(new Distinct(), new Sorted(), new StringMapper()), new TextFileWriter());
	}
}