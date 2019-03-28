package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class FibonacciGeneratorTest {

	@Test
	public void testExecution() {
		final var operation = new FibonacciGenerator();

		final var result = operation.get().limit(5).toArray(Long[]::new);

		assertEquals(1, result[0].longValue());
		assertEquals(2, result[1].longValue());
		assertEquals(3, result[2].longValue());
		assertEquals(5, result[3].longValue());
		assertEquals(8, result[4].longValue());
	}

}
