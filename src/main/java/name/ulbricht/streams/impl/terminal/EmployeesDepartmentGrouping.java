package name.ulbricht.streams.impl.terminal;

import java.beans.JavaBean;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;
import name.ulbricht.streams.impl.Employee;

@JavaBean(description = "Groups all employees by their department.")
@Terminal
public final class EmployeesDepartmentGrouping implements Function<Stream<Employee>, Map<String, List<Employee>>> {

	@Override
	public Map<String, List<Employee>> apply(final Stream<Employee> stream) {
		return stream.collect(Collectors.groupingBy(Employee::getDepartment));
	}

	@Override
	public String toString() {
		return ".collect(Collectors.groupingBy(Employee::getDepartment))";
	}
}
