package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.Employee;

@StreamOperation(name = "Employees Sorter", type = INTERMEDIATE, input = Employee.class, output = Employee.class, description = "Sorts employees by their last name.")
public final class EmployeesSorter implements Function<Stream<Employee>, Stream<Employee>> {

	@Override
	public Stream<Employee> apply(final Stream<Employee> stream) {
		return stream.sorted(Comparator.comparing(Employee::getLastName));
	}

	@Override
	public String toString() {
		return ".sorted(Comparator.comparing(Employee::getLastName))";
	}
}
