package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public final class TextLinesTest {

	@Test
	public void testExecution() {
		final var operation = new TextLines("Hello\nWorld");

		final var result = operation.get().toArray(String[]::new);

		assertArrayEquals(new String[] { "Hello", "World" }, result);
	}

}
