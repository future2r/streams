package name.ulbricht.streams.api;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface StreamOperationsProvider {

	Stream<Class<?>> getSourceOperations();

	Stream<Class<?>> getIntermediateOperations();

	Stream<Class<?>> getTerminalOperations();

	Map<String, Supplier<StreamOperationSet>> getPresets();
}
