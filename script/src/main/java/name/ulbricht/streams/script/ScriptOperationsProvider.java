package name.ulbricht.streams.script;

import java.util.List;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperationsPreset;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;
import name.ulbricht.streams.api.StreamOperationsSet;
import name.ulbricht.streams.api.basic.Sorted;
import name.ulbricht.streams.api.basic.StringLines;
import name.ulbricht.streams.api.basic.SystemOut;
import name.ulbricht.streams.api.basic.SystemOutPeek;
import name.ulbricht.streams.extended.Modules;
import name.ulbricht.streams.extended.StringFormatter;
import name.ulbricht.streams.extended.SystemProperties;

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
	public Stream<StreamOperationsPreset> getPresets() {
		return Stream.of(
				new StreamOperationsPreset("Filter by JavaScript", () -> new StreamOperationsSet(new StringLines(),
						List.of(new JavaScriptFilter<>("pass = element.indexOf('H') >= 0")), new SystemOut<>())),
				new StreamOperationsPreset("Display Java system properties",
						() -> new StreamOperationsSet(new SystemProperties(),
								List.of(new Sorted<>(), new JavaScriptMap<>(
										"mapped = element + \":\\t\" + java.lang.System.getProperty(element);")),
								new SystemOut<>())),
				new StreamOperationsPreset("Modules and exported packages",
						() -> new StreamOperationsSet(new Modules(), List.of(new JavaScriptSorted<>(
								"result = java.util.Objects.compare(element1.getName(), element2.getName(), java.util.Comparator.naturalOrder());"),
								new SystemOutPeek<>(),
								new JavaScriptFlatMap<>("mappedStream = element.getDescriptor().exports().stream();"),
								new StringFormatter<>(" -> %s")), new SystemOut<>())));
	}
}
