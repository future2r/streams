package name.ulbricht.streams.api;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface StreamOperationsProvider {

	List<Class<?>> getSourceOperations();

	List<Class<?>> getIntermediateOperations();

	List<Class<?>> getTerminalOperations();
	
	Map<String, Supplier<StreamOperationSet>> getPresets();
}
