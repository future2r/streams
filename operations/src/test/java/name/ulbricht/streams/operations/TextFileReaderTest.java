package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public final class TextFileReaderTest {

	@Test
	public void testExecution() {
		final var operation = new TextFileReader(Paths.get(System.getProperty("user.dir"), "testfiles", "text.txt"),
				StandardCharsets.UTF_8);

		final var result = operation.get().toArray(String[]::new);

		assertArrayEquals(new String[] { "Hello", "World" }, result);
	}

}
