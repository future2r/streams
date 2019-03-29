package name.ulbricht.streams.api;

import java.util.stream.Stream;

public interface StreamOperationsProvider {

	Stream<Class<?>> getSourceOperations();

	Stream<Class<?>> getIntermediateOperations();

	Stream<Class<?>> getTerminalOperations();

	Stream<StreamOperationsPreset> getPresets();
}
