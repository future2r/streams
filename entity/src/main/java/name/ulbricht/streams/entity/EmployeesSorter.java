package name.ulbricht.streams.entity;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Sorts employees by their last name.")
@Intermediate
public final class EmployeesSorter implements Function<Stream<Employee>, Stream<Employee>> {

	private Field field;

	public EmployeesSorter() {
		this(Field.LAST_NAME);
	}

	public EmployeesSorter(final Field field) {
		this.field = Objects.requireNonNull(field, "field must not be null");
	}

	@BeanProperty(description = "The field the employees are sorted by")
	public Field getField() {
		return this.field;
	}

	public void setField(final Field field) {
		this.field = Objects.requireNonNull(field, "field must not be null");
	}

	@Override
	public Stream<Employee> apply(final Stream<Employee> stream) {
		switch (this.field) {
		case LAST_NAME:
			return stream.sorted(Comparator.comparing(Employee::getLastName));
		case FIRST_NAME:
			return stream.sorted(Comparator.comparing(Employee::getFirstName));
		case DEPARTMENT:
			return stream.sorted(Comparator.comparing(Employee::getDepartment));
		case SALARY:
			return stream.sorted(Comparator.comparing(Employee::getSalary));
		default:
			throw new IllegalArgumentException(this.field.name());
		}
	}

	@Override
	public String toString() {
		return String.format(".sorted(Comparator.comparing(Employee::get%s))", this.field.fieldName());
	}
}
