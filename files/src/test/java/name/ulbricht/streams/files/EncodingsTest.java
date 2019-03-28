package name.ulbricht.streams.files;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import name.ulbricht.streams.files.Encodings;

public final class EncodingsTest {

	@Test
	public void testExecution() {
		final var operation = new Encodings();

		assertTrue(operation.get().count() > 0);
	}

}
