package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import name.ulbricht.streams.api.basic.StringLines;

public final class StringLinesTest {

	@Test
	public void testExecution() {
		final var operation = new StringLines("Hello\nWorld");

		final var result = operation.get().toArray(String[]::new);

		assertArrayEquals(new String[] { "Hello", "World" }, result);
	}

}
