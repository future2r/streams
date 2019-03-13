package name.ulbricht.streams.impl;

import java.util.List;
import java.util.function.Supplier;

import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.impl.intermediate.Distinct;
import name.ulbricht.streams.impl.intermediate.FileLines;
import name.ulbricht.streams.impl.intermediate.JavaScriptFilter;
import name.ulbricht.streams.impl.intermediate.JavaScriptFlatMap;
import name.ulbricht.streams.impl.intermediate.JavaScriptMap;
import name.ulbricht.streams.impl.intermediate.LowerCase;
import name.ulbricht.streams.impl.intermediate.RegExSplitter;
import name.ulbricht.streams.impl.intermediate.Sorted;
import name.ulbricht.streams.impl.intermediate.SystemOutPeek;
import name.ulbricht.streams.impl.intermediate.ToStringMapper;
import name.ulbricht.streams.impl.source.Empty;
import name.ulbricht.streams.impl.source.FindFiles;
import name.ulbricht.streams.impl.source.Modules;
import name.ulbricht.streams.impl.source.RandomIntegerGenerator;
import name.ulbricht.streams.impl.source.SystemProperties;
import name.ulbricht.streams.impl.source.TextFileReader;
import name.ulbricht.streams.impl.source.TextLines;
import name.ulbricht.streams.impl.terminal.Count;
import name.ulbricht.streams.impl.terminal.StringLengthGrouping;
import name.ulbricht.streams.impl.terminal.SystemOut;
import name.ulbricht.streams.impl.terminal.TextFileWriter;

public enum Preset {

	DEFAULT("Default", Preset::createDefault),

	SPLIT_WORDS("Word length statistics", Preset::createSplitWords),

	FILTER_JAVA_SCRIPT("Filter by JavaScript", Preset::createFilterJavaScript),

	SORT_LINES("Sort lines in a file", Preset::createSortLines),

	COUNT_LINES("Count lines in all files", Preset::createCountLines),

	GENERATE_NUMBERS("Generate a file with sorted numbers", Preset::createGenerateNumbers),

	SYSTEM_PROPERTIES("Display Java system properties", Preset::createSystemProperties),

	MODULE_PACKAGES("Modules and packages", Preset::createModulesAndPackages);

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

	private static StreamOperationSet createFilterJavaScript() {
		final var filter = new JavaScriptFilter();
		filter.setScript("result = element.indexOf('H') >= 0");

		return new StreamOperationSet(new TextLines(), List.of(filter), new SystemOut());
	}

	private static StreamOperationSet createSortLines() {
		return new StreamOperationSet(new TextFileReader(), List.of(new Distinct(), new Sorted()),
				new TextFileWriter());
	}

	private static StreamOperationSet createCountLines() {
		final var fileFilter = new JavaScriptFilter();
		fileFilter.setScript("result = element.getFileName().toString().endsWith(\".java\")");

		return new StreamOperationSet(new FindFiles(), List.of(fileFilter, new FileLines()), new Count());
	}

	private static StreamOperationSet createGenerateNumbers() {
		return new StreamOperationSet(new RandomIntegerGenerator(),
				List.of(new Distinct(), new Sorted(), new ToStringMapper()), new TextFileWriter());
	}

	private static StreamOperationSet createSystemProperties() {
		final var propertyReader = new JavaScriptMap();
		propertyReader.setScript("result = element + \":\\t\" + java.lang.System.getProperty(element);");

		return new StreamOperationSet(new SystemProperties(), List.of(new Sorted(), propertyReader), new SystemOut());
	}

	private static StreamOperationSet createModulesAndPackages() {
		JavaScriptFlatMap packageExtractor = new JavaScriptFlatMap();
		packageExtractor.setScript("result = element.getPackages().stream();");

		return new StreamOperationSet(new Modules(), List.of(new SystemOutPeek(), packageExtractor), new SystemOut());
	}
}
