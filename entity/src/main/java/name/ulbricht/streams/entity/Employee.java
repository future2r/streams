package name.ulbricht.streams.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Employee {

	public static Employee[] employees() {
		try (final var is = Employee.class.getResourceAsStream("employees.csv");
				final var isr = new InputStreamReader(is);
				final var br = new BufferedReader(isr)) {
			return br.lines().filter(line -> !line.isEmpty()).map(line -> line.split(","))
					.map(record -> new Employee(record[0], record[1], record[2], Integer.parseInt(record[3])))
					.toArray(Employee[]::new);
		} catch (final IOException ex) {
			return new Employee[0];
		}
	}

	private String lastName;
	private String firstName;
	private String department;
	private int salary;

	public Employee(final String lastName, final String firstName, final String department, final int salary) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.department = department;
		this.salary = salary;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(final String department) {
		this.department = department;
	}

	public int getSalary() {
		return this.salary;
	}

	public void setSalary(final int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return String.format("%s, %s from %s earns %d", this.lastName, this.firstName, this.department, this.salary);
	}
}
