package name.ulbricht.streams.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class EmployeeTest {

	@Test
	public void checkReadFile() {
		assertEquals(9, Employee.employees().length);
	}
}
