package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class RegExSplitterTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("Hello World");

		final var operation = new RegExSplitter("l");

		final var result = operation.apply(stream).toArray(String[]::new);

		assertArrayEquals(new String[] { "He", "", "o Wor", "d" }, result);
	}

}
