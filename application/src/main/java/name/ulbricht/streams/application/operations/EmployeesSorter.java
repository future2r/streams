package name.ulbricht.streams.application.operations;

import java.beans.JavaBean;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Sorts employees by their last name.")
@Intermediate
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
