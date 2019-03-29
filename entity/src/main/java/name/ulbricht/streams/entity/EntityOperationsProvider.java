package name.ulbricht.streams.entity;

import java.util.List;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperationsPreset;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.StreamOperationsScanner;
import name.ulbricht.streams.api.StreamOperationsSet;

public class EntityOperationsProvider implements StreamOperationsProvider {

	private static final String MODULE_NAME = "name.ulbricht.streams.entity";

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
		return Stream.of(new StreamOperationsPreset("Group employees by department",
				() -> new StreamOperationsSet(new Employees(),
						List.of(new EmployeesSalaryFilter(), new EmployeesSorter(Field.LAST_NAME)),
						new EmployeesGrouping(Field.DEPARTMENT))));
	}
}