package name.ulbricht.streams.application.operations;

public final class Employee {

	public static Employee[] employees() {
		return new Employee[] { new Employee("John", "Smith", "New York Headquarters", 4500),
				new Employee("Jane", "Smith", "New York Headquarters", 5000),
				new Employee("Jason", "Wilkinson", "New York Headquarters", 3300),
				new Employee("Jack", "Dawson", "San Francisco Branch", 1900),
				new Employee("Jerry", "Gold", "San Francisco Branch", 2300),
				new Employee("Jenny", "Hunter", "San Francisco Branch", 2300),
				new Employee("James", "Miller", "L.A. Store", 2000),
				new Employee("Jakob", "Walker", "L.A. Store", 1800),
				new Employee("Jasmin", "Ryan", "L.A. Store", 3200) };
	}

	private String firstName;
	private String lastName;
	private String department;
	private int salary;

	public Employee(final String firstName, final String lastName, final String department, final int salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.salary = salary;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
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
		return String.format("%s %s from %s earns %d", this.firstName, this.lastName, this.department, this.salary);
	}
}
