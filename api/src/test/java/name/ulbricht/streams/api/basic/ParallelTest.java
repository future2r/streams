package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class ParallelTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test").sequential();
		assertFalse(stream.isParallel());

		final var operation = new Parallel<String>();

		assertTrue(operation.apply(stream).isParallel());
	}
}
