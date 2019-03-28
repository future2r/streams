package name.ulbricht.streams.files;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;
import name.ulbricht.streams.api.basic.Count;
import name.ulbricht.streams.api.basic.Distinct;
import name.ulbricht.streams.api.basic.Sorted;

public class FilesOperationsProvider implements StreamOperationsProvider {

	private static final String MODULE_NAME = "name.ulbricht.streams.files";

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
				"Count lines in all files", FilesOperationsProvider::createCountLines, //
				"Sort lines in a file", FilesOperationsProvider::createSortLines //
		);
	}

	private static StreamOperationSet createCountLines() {
		return new StreamOperationSet(new FileFinder(),
				List.of(new FileFilter(FileFilter.Syntax.GLOB, "**/*.java"), new FileLines()), new Count<>());
	}

	private static StreamOperationSet createSortLines() {
		return new StreamOperationSet(new FileReader(), List.of(new Distinct<>(), new Sorted<>()), new FileWriter());
	}
}
