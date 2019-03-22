package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class EmptyTest {

	@Test
	public void testExecution() {
		final var operation = new Empty<String>();
		
		assertEquals(0, operation.get().count());
	}

}
