package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.Employee;

@JavaBean(description = "Creates a stream with all employees.")
@StreamOperation(type = SOURCE, output = Employee.class)
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
