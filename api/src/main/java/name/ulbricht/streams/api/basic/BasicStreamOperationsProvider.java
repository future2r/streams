package name.ulbricht.streams.api.basic;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;

public class BasicStreamOperationsProvider implements StreamOperationsProvider {

	private static final String MODULE_NAME = "name.ulbricht.streams.api";

	@Override
	public List<Class<?>> getSourceOperations() {
		return StreamOperationsScanner.findSourceOperations(MODULE_NAME);
	}

	@Override
	public List<Class<?>> getIntermediateOperations() {
		return StreamOperationsScanner.findIntermediateOperations(MODULE_NAME);
	}

	@Override
	public List<Class<?>> getTerminalOperations() {
		return StreamOperationsScanner.findTerminalOperations(MODULE_NAME);
	}

	@Override
	public Map<String, Supplier<StreamOperationSet>> getPresets() {
		return Map.of( //
				"Print integers raNGE", BasicStreamOperationsProvider::createDefault);
	}

	private static StreamOperationSet createDefault() {
		return new StreamOperationSet(new IntegerRange(), List.of(new SleepPeek<>()), new SystemOut<>());
	}
}
