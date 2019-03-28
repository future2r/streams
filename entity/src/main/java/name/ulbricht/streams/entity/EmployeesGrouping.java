package name.ulbricht.streams.entity;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Groups all employees by their department.")
@Terminal
public final class EmployeesGrouping implements Function<Stream<Employee>, Map<Object, List<Employee>>> {

	private Field field;

	public EmployeesGrouping() {
		this(Field.LAST_NAME);
	}

	public EmployeesGrouping(final Field field) {
		this.field = Objects.requireNonNull(field, "field must not be null");
	}

	@BeanProperty(description = "The field the employees are grouped by")
	public Field getField() {
		return this.field;
	}

	public void setField(final Field field) {
		this.field = Objects.requireNonNull(field, "field must not be null");
	}

	@Override
	public Map<Object, List<Employee>> apply(final Stream<Employee> stream) {
		switch (this.field) {
		case LAST_NAME:
			return stream.collect(Collectors.groupingBy(Employee::getLastName));
		case FIRST_NAME:
			return stream.collect(Collectors.groupingBy(Employee::getFirstName));
		case DEPARTMENT:
			return stream.collect(Collectors.groupingBy(Employee::getDepartment));
		case SALARY:
			return stream.collect(Collectors.groupingBy(Employee::getSalary));
		default:
			throw new IllegalArgumentException(this.field.name());
		}
	}

	@Override
	public String toString() {
		return String.format(".collect(Collectors.groupingBy(Employee::get%s))", this.field.fieldName());
	}
}
