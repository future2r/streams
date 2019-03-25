package name.ulbricht.streams.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class EmployeesTest {

	@Test
	public void testExecution() {
		final var operation = new Employees();

		assertTrue(operation.get().count() > 0);
	}
}
