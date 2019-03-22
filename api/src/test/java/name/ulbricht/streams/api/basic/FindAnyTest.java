package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class FindAnyTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new FindAny<String>();

		final var result = operation.apply(stream);

		assertTrue(result.isPresent());
	}

	@Test
	public void testEmptyStream() {
		final var operation = new FindAny<String>();

		final var result = operation.apply(Stream.empty());

		assertFalse(result.isPresent());
	}

}
