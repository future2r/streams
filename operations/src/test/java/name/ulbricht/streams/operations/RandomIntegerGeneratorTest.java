package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class RandomIntegerGeneratorTest {

	@Test
	public void testExecution() {
		final var operation = new RandomIntegerGenerator(5);

		assertEquals(5, operation.get().count());
	}

}
