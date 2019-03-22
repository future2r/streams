package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class RandomDoubleGeneratorTest {

	@Test
	public void testExecution() {
		final var operation = new RandomDoubleGenerator(5);

		assertEquals(5, operation.get().count());
	}

}
