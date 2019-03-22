package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public final class JavaScriptIteratorTest {

	@Test
	public void testExecution() {
		final var operation = new JavaScriptIterator<String>("seed = \"A\";", "hasNext = element.length() <= 5;",
				"next = element + \"A\"");

		final var result = operation.get().toArray(String[]::new);

		assertArrayEquals(new String[] { "A", "AA", "AAA", "AAAA", "AAAAA" }, result);
	}
}
