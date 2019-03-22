package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class PowersOfTwoTest {

	@Test
	public void testExecution() {
		final var operation = new PowersOfTwo();

		final var result = operation.get().toArray(Long[]::new);

		assertEquals(1, result[0].longValue());
		assertEquals(2, result[1].longValue());
		assertEquals(4, result[2].longValue());
		assertEquals(8, result[3].longValue());
		assertEquals(16, result[4].longValue());
		
		assertEquals(2_305_843_009_213_693_952L, result[61].longValue());
	}

}
