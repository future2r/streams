package name.ulbricht.streams.extended;

import java.util.List;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperationsPreset;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;
import name.ulbricht.streams.api.StreamOperationsSet;
import name.ulbricht.streams.api.basic.LowerCase;
import name.ulbricht.streams.api.basic.StringLines;

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
	public Stream<StreamOperationsPreset> getPresets() {
		return Stream.of(new StreamOperationsPreset("Word length statistics", () -> new StreamOperationsSet(
				new StringLines(
						"Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. "
								+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
								+ "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
								+ "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
				List.of(new RegExSplitter(), new LowerCase()), new StringLengthGrouping())));
	}
}
