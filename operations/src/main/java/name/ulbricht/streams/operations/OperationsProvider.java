package name.ulbricht.streams.operations;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;
import name.ulbricht.streams.api.basic.Distinct;
import name.ulbricht.streams.api.basic.Sorted;
import name.ulbricht.streams.api.basic.SystemOut;

public class OperationsProvider implements StreamOperationsProvider {

	private static final String MODULE_NAME = "name.ulbricht.streams.operations";

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
				"Integers", OperationsProvider::createDefault, //
				"Word length statistics", OperationsProvider::createSplitWords, //
				"Sort lines in a file", OperationsProvider::createSortLines, //
				"Group employees by department", OperationsProvider::createEmployeesByDepartment, //
				"Generate a file with sorted numbers", OperationsProvider::createGenerateNumbers //
		);
	}

	private static StreamOperationSet createDefault() {
		return new StreamOperationSet(new IntegerRange(), List.of(new SleepPeek<>()), new SystemOut<>());
	}

	private static StreamOperationSet createSplitWords() {
		final var source = new TextLines();
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

	private static StreamOperationSet createEmployeesByDepartment() {
		return new StreamOperationSet(new Employees(), List.of(new EmployeesFilter(), new EmployeesSorter()),
				new EmployeesDepartmentGrouping());
	}

	private static StreamOperationSet createGenerateNumbers() {
		return new StreamOperationSet(new RandomIntegerGenerator(),
				List.of(new Distinct<>(), new Sorted<>(), new ToStringMapper<>()), new TextFileWriter());
	}
}
