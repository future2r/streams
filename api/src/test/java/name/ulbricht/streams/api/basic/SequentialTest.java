package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class SequentialTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test").parallel();
		assertTrue(stream.isParallel());

		final var operation = new Sequential<String>();

		assertFalse(operation.apply(stream).isParallel());
	}
}
