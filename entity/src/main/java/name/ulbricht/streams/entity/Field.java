package name.ulbricht.streams.entity;

public enum Field {
	LAST_NAME("LastName"), FIRST_NAME("FirstName"), DEPARTMENT("Department"), SALARY("Salary");

	private final String fieldName;

	private Field(final String fieldName) {
		this.fieldName = fieldName;
	}

	public String fieldName() {
		return this.fieldName;
	}
}