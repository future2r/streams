package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.impl.Employee;

@JavaBean(description = "Filters employees by their salary.")
@StreamOperation(type = INTERMEDIATE, input = Employee.class, output = Employee.class)
public final class EmployeesFilter implements Function<Stream<Employee>, Stream<Employee>> {

	private int salary = 2000;

	@BeanProperty(description = "Monthly salary")
	public int getSalary() {
		return this.salary;
	}

	public void setSalary(final int salary) {
		this.salary = salary;
	}

	@Override
	public Stream<Employee> apply(final Stream<Employee> stream) {
		return stream.filter(e -> e.getSalary() >= this.salary);
	}

	@Override
	public String toString() {
		return String.format(".filter(e -> e.getSalary() >= %s)", Integer.toString(this.salary));
	}
}
