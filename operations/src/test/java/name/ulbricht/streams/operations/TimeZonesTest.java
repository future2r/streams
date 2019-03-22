package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class TimeZonesTest {

	@Test
	public void testExecution() {
		final var operation = new TimeZones();

		assertTrue(operation.get().count() > 0);
	}

}
