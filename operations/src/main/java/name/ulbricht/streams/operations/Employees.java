package name.ulbricht.streams.operations;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Creates a stream with all employees.")
@Source
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
