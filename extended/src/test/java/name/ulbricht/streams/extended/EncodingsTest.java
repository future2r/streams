package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class EncodingsTest {

	@Test
	public void testExecution() {
		final var operation = new Encodings();

		assertTrue(operation.get().count() > 0);
	}

}
