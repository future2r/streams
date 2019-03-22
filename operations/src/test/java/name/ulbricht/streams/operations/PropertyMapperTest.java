package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class PropertyMapperTest {

	@Test
	public void testExecution() {
		final var stream = Stream.<Object>of("Hello", 42);

		final var operation = new PropertyMapper("class");

		final var result = operation.apply(stream).toArray();

		assertArrayEquals(new Object[] { String.class, Integer.class }, result);
	}

}
