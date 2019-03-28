package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public final class CharactersTest {

	@Test
	public void testExecution() {
		final var operation = new Characters("Hello");

		final var result = operation.get().toArray(Integer[]::new);

		assertArrayEquals(new Integer[] { (int) 'H', (int) 'e', (int) 'l', (int) 'l', (int) 'o' }, result);
	}

}
