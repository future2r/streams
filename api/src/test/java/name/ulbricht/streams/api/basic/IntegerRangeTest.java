package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public final class IntegerRangeTest {

	@Test
	public void testExecution() {
		final var operation = new IntegerRange(42, 46, true);

		final var result = operation.get().toArray(Integer[]::new);

		assertArrayEquals(new Integer[] { 42, 43, 44, 45, 46 }, result);
	}
}
