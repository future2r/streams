package name.ulbricht.streams.api.basic;

import java.util.List;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperationsPreset;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;
import name.ulbricht.streams.api.StreamOperationsSet;

public class BasicStreamOperationsProvider implements StreamOperationsProvider {

	private static final String MODULE_NAME = "name.ulbricht.streams.api";

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
		return Stream.of(new StreamOperationsPreset("Print integers range",
				() -> new StreamOperationsSet(new IntegerRange(), List.of(new SleepPeek<>()), new SystemOut<>())));
	}
}
