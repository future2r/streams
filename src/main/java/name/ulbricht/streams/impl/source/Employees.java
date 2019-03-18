package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.Employee;

@StreamOperation(name = "Employees List", type = SOURCE, output = Employee.class, description = "Creates a stream with all employees.")
public final class Employees implements Supplier<Stream<Employee>> {

	@Override
	public Stream<Employee> get() {
		return Stream.of(Employee.employees());
	}

	@Override
	public String toString() {
		return "Stream.of(Employee.employees())";
	}
}
