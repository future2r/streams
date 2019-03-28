package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public final class LongRangeTest {

	@Test
	public void testExecution() {
		final var operation = new LongRange(42, 46, true);

		final var result = operation.get().toArray(Long[]::new);

		assertArrayEquals(new Long[] { 42L, 43L, 44L, 45L, 46L }, result);
	}
}
