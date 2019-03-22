package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class SleepPeekTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new SleepPeek<String>(200);

		final long startTime = System.currentTimeMillis();
		operation.apply(stream).forEach(e -> {
		});

		assertTrue((System.currentTimeMillis() - startTime) >= 800);
	}

}
