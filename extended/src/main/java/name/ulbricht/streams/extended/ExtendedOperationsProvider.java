package name.ulbricht.streams.extended;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;
import name.ulbricht.streams.api.basic.Distinct;
import name.ulbricht.streams.api.basic.LowerCase;
import name.ulbricht.streams.api.basic.Sorted;
import name.ulbricht.streams.api.basic.StringLines;
import name.ulbricht.streams.api.basic.ToString;

public class ExtendedOperationsProvider implements StreamOperationsProvider {

	private static final String MODULE_NAME = "name.ulbricht.streams.extended";

	@Override
	public Stream<Class<?>> getSourceOperations() {
		return StreamOperationsScanner.findSourceOperations(MODULE_NAME);
	}

	@Override
	public Stream<Class<?>> getIntermediateOperations() {
		return StreamOperationsScanner.findIntermediateOperations(MODULE_NAME);
	}

	@Override
	public Stream<Class<?>> getTerminalOperations() {
		return StreamOperationsScanner.findTerminalOperations(MODULE_NAME);
	}

	@Override
	public Map<String, Supplier<StreamOperationSet>> getPresets() {
		return Map.of( //
				"Word length statistics", ExtendedOperationsProvider::createSplitWords, //
				"Sort lines in a file", ExtendedOperationsProvider::createSortLines, //
				"Generate a file with sorted numbers", ExtendedOperationsProvider::createGenerateNumbers //
		);
	}

	private static StreamOperationSet createSplitWords() {
		final var source = new StringLines();
		source.setText(
				"Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. "
						+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
						+ "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
						+ "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

		return new StreamOperationSet(source, List.of(new RegExSplitter(), new LowerCase()),
				new StringLengthGrouping());
	}

	private static StreamOperationSet createSortLines() {
		return new StreamOperationSet(new TextFileReader(), List.of(new Distinct<>(), new Sorted<>()),
				new TextFileWriter());
	}

	private static StreamOperationSet createGenerateNumbers() {
		return new StreamOperationSet(new RandomIntegerGenerator(),
				List.of(new Distinct<>(), new Sorted<>(), new ToString<>()), new TextFileWriter());
	}
}
