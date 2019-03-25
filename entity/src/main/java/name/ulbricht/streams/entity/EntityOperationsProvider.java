package name.ulbricht.streams.entity;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;

public class EntityOperationsProvider implements StreamOperationsProvider {

	private static final String MODULE_NAME = "name.ulbricht.streams.entity";

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
				"Group employees by department", EntityOperationsProvider::createEmployeesByDepartment //
		);
	}

	private static StreamOperationSet createEmployeesByDepartment() {
		return new StreamOperationSet(new Employees(), List.of(new EmployeesFilter(), new EmployeesSorter()),
				new EmployeesDepartmentGrouping());
	}
}