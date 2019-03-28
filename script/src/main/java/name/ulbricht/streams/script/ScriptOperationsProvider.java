package name.ulbricht.streams.script;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;
import name.ulbricht.streams.api.basic.Count;
import name.ulbricht.streams.api.basic.Sorted;
import name.ulbricht.streams.api.basic.StringLines;
import name.ulbricht.streams.api.basic.SystemOut;
import name.ulbricht.streams.api.basic.SystemOutPeek;
import name.ulbricht.streams.operations.FileLines;
import name.ulbricht.streams.operations.FindFiles;
import name.ulbricht.streams.operations.Modules;
import name.ulbricht.streams.operations.StringFormatter;
import name.ulbricht.streams.operations.SystemProperties;

public class ScriptOperationsProvider implements StreamOperationsProvider {

	private static final String MODULE_NAME = "name.ulbricht.streams.script";

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
				"Count lines in all files", ScriptOperationsProvider::createCountLines, //
				"Filter by JavaScript", ScriptOperationsProvider::createFilterJavaScript, //
				"Display Java system properties", ScriptOperationsProvider::createSystemProperties, //
				"Modules and exported packages", ScriptOperationsProvider::createModulesAndPackages //
		);
	}

	private static StreamOperationSet createCountLines() {
		return new StreamOperationSet(new FindFiles(),
				List.of(new JavaScriptFilter<>("pass = element.getFileName().toString().endsWith(\".java\")"),
						new FileLines()),
				new Count<>());
	}

	private static StreamOperationSet createFilterJavaScript() {
		return new StreamOperationSet(new StringLines(),
				List.of(new JavaScriptFilter<>("pass = element.indexOf('H') >= 0")), new SystemOut<>());
	}

	private static StreamOperationSet createSystemProperties() {
		return new StreamOperationSet(new SystemProperties(),
				List.of(new Sorted<>(),
						new JavaScriptMap<>("mapped = element + \":\\t\" + java.lang.System.getProperty(element);")),
				new SystemOut<>());
	}

	private static StreamOperationSet createModulesAndPackages() {
		return new StreamOperationSet(new Modules(), List.of(new JavaScriptSorted<>(
				"result = java.util.Objects.compare(element1.getName(), element2.getName(), java.util.Comparator.naturalOrder());"),
				new SystemOutPeek<>(),
				new JavaScriptFlatMap<>("mappedStream = element.getDescriptor().exports().stream();"),
				new StringFormatter<>(" -> %s")), new SystemOut<>());
	}
}
