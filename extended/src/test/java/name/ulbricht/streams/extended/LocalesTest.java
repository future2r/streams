package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class LocalesTest {

	@Test
	public void testExecution() {
		final var operation = new Locales();

		assertTrue(operation.get().count() > 0);
	}

}
