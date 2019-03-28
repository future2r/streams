package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class SystemPropertiesTest {

	@Test
	public void testExecution() {
		final var operation = new SystemProperties();

		assertTrue(operation.get().count() > 0);
	}

}
